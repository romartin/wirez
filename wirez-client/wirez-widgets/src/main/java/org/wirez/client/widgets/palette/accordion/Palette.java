/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wirez.client.widgets.palette.accordion;

import com.ait.lienzo.client.widget.LienzoPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.uberfire.client.mvp.UberView;
import org.uberfire.client.workbench.widgets.common.ErrorPopupPresenter;
import org.wirez.client.widgets.event.AddShapeToCanvasEvent;
import org.wirez.client.widgets.palette.accordion.group.PaletteGroup;
import org.wirez.client.widgets.palette.accordion.group.PaletteGroupItem;
import org.wirez.client.widgets.palette.tooltip.PaletteTooltip;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.service.definition.DefinitionSetResponse;
import org.wirez.core.client.Shape;
import org.wirez.core.client.ShapeGlyph;
import org.wirez.core.client.ShapeSet;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.canvas.control.HasShapeGlyphDragHandler;
import org.wirez.core.client.canvas.control.ShapeGlyphDragHandler;
import org.wirez.core.client.factory.ShapeFactory;
import org.wirez.core.client.service.ClientDefinitionServices;
import org.wirez.core.client.service.ClientRuntimeError;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.*;

@Dependent
public class Palette implements IsWidget {

    public interface View extends UberView<Palette> {
        
        View setNoCanvasViewVisible(boolean isVisible);
        
        View setGroupsViewVisible(boolean isVisible);
        
        View addGroup(IsWidget paletteGroupView);

        View clearGroups();
        
        View clear();

    }

    ShapeManager shapeManager;
    ClientDefinitionServices clientDefinitionServices;
    Event<AddShapeToCanvasEvent> addShapeToCanvasEvent;
    SyncBeanManager beanManager;
    PaletteTooltip paletteTooltip;
    ErrorPopupPresenter errorPopupPresenter;
    View view;
    
    @Inject
    public Palette(final View view,
                   final ErrorPopupPresenter errorPopupPresenter,
                   final ShapeManager shapeManager,
                   final ClientDefinitionServices clientDefinitionServices,
                   final SyncBeanManager beanManager,
                   final Event<AddShapeToCanvasEvent> addShapeToCanvasEvent,
                   final PaletteTooltip paletteTooltip) {
        this.view = view;
        this.errorPopupPresenter = errorPopupPresenter;
        this.shapeManager = shapeManager;
        this.clientDefinitionServices = clientDefinitionServices;
        this.beanManager = beanManager;
        this.addShapeToCanvasEvent = addShapeToCanvasEvent;
        this.paletteTooltip = paletteTooltip;
    }

    @PostConstruct
    public void init() {
        view.init(this);
        showNoCanvasState();
    }
    
    @Override
    public Widget asWidget() {
        return view.asWidget();
    }

    public void showNoCanvasState() {
        clear();
        view.setGroupsViewVisible(false);
        view.setNoCanvasViewVisible(true);
    }
    
    public void show(final int width, final String shapeSetId) {
        clear();
        final ShapeSet wirezShapeSet = getShapeSet(shapeSetId);
        final String definitionSetId = wirezShapeSet.getDefinitionSetId();
        
        clientDefinitionServices.getDefinitionSet(definitionSetId, new ClientDefinitionServices.ServiceCallback<DefinitionSetResponse>() {
            @Override
            public void onSuccess(final DefinitionSetResponse definitionSetResponse) {
                doShow(width, wirezShapeSet, definitionSetResponse.getDefinitionSet(), definitionSetResponse.getDefinitions());
                view.setNoCanvasViewVisible(false);
                view.setGroupsViewVisible(true);
            }

            @Override
            public void onError(final ClientRuntimeError error) {
                showError(error);
            }
        });
        
    }
    
    private ShapeSet getShapeSet(final String shapeSetId) {
        final Collection<ShapeSet> sets = shapeManager.getShapeSets();
        for (final ShapeSet set  : sets) {
            if (set.getId().equals(shapeSetId)) {
                return set;
            }
        }
        return null;
    }
    
    public void clear() {
        view.clear();
    }

    private void doShow(final int width, final ShapeSet wirezShapeSet, final DefinitionSet definitionSet, final Collection<Definition> definitions) {

        // Clear current palette groups.
        view.clearGroups();

        final Collection<ShapeFactory<? extends Definition, ? extends Shape>> factories = wirezShapeSet.getFactories();

        // Load entries.
        final Map<String, List<PaletteGroupItem>> paletteGroupItems = new HashMap<>();
        for (final ShapeFactory<? extends Definition, ? extends Shape> factory : factories) {
            final Definition definition = getDefinition(definitions, factory);

            if ( null != definition ) {

                final String category = definition.getDefinitionContent().getCategory();
                final String description = factory.getDescription();
                final ShapeGlyph glyph = factory.getGlyph();

                List<PaletteGroupItem> items = paletteGroupItems.get(category);
                if ( null == items ) {
                    items = new LinkedList<>();
                    paletteGroupItems.put(category, items);
                }
                
                

                PaletteGroupItem paletteGroupItem = PaletteGroup.buildItem(glyph, description,
                        new PaletteGroupItem.Handler() {

                            @Override
                            public void onFocus(double x, double y) {
                                paletteTooltip.show(glyph,description,  width + 5, y - 50);
                            }

                            @Override
                            public void onLostFocus() {
                                paletteTooltip.hide();
                            }

                            /**
                             * Add the shape into the canvas diagram when mouse click at a fixed position.
                             */
                            @Override
                            public void onClick() {
                                GWT.log("Palette: Adding " + description);
                                addShapeToCanvasEvent.fire(new AddShapeToCanvasEvent(definition, factory));
                            }

                            /**
                             * Add the shape into the canvas diagram using drag features. 
                             * Drag proxy and final position into the canvas is given by the glyph drag handler implementation.
                             */
                            @Override
                            public void onDragStart(final LienzoPanel parentPanel, final double x, final double y) {

                                if (factory instanceof HasShapeGlyphDragHandler) {
                                    final HasShapeGlyphDragHandler hasShapeGlyphDragHandler = (HasShapeGlyphDragHandler) factory;
                                    final ShapeGlyphDragHandler shapeGlyphDragHandler = hasShapeGlyphDragHandler.getShapeGlyphDragHandler();

                                    shapeGlyphDragHandler.show(parentPanel, glyph, x, y, new ShapeGlyphDragHandler.Callback() {
                                        @Override
                                        public void onMove(final LienzoPanel floatingPanel, final double x, final double y) {

                                        }

                                        @Override
                                        public void onComplete(final LienzoPanel floatingPanel, final double x, final double y) {
                                            GWT.log("Palette: Adding " + description + " at " + x + "," + y);
                                            addShapeToCanvasEvent.fire(new AddShapeToCanvasEvent(definition, factory, x - width, y));
                                        }
                                    });
                                    
                                }

                            }
                        });

                items.add(paletteGroupItem);
                
            }
            
        }
        
        // Show palette groups.
        if (!paletteGroupItems.isEmpty()) {
            final Set<Map.Entry<String, List<PaletteGroupItem>>> entries = paletteGroupItems.entrySet();
            for (final Map.Entry<String, List<PaletteGroupItem>> entry : entries) {
                final String category = entry.getKey();
                final List<PaletteGroupItem> items = entry.getValue();

                PaletteGroup paletteGroup = buildPaletteGroup();
                paletteGroup.show(category, width, items);
                view.addGroup(paletteGroup);
            }
        }
        
    }
    
    private Definition getDefinition(final Collection<Definition> definitions, final ShapeFactory factory) {
        for (final Definition definition : definitions) {
            if (factory.accepts(definition)) {
                return definition;
            }
        }
        return null;
    }
    
    private PaletteGroup buildPaletteGroup() {
        return beanManager.lookupBean( PaletteGroup.class ).newInstance();
    }

    void showError(final ClientRuntimeError error) {
        final String message = error.getCause() != null ? error.getCause() : error.getMessage();
        showError(message);
    }

    void showError(final String message) {
        errorPopupPresenter.showMessage(message);
    }
    
}
