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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.uberfire.client.mvp.UberView;
import org.uberfire.mvp.Command;
import org.wirez.client.widgets.event.PaletteShapeSelectedEvent;
import org.wirez.client.widgets.palette.accordion.group.PaletteGroup;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.client.Shape;
import org.wirez.core.client.ShapeGlyph;
import org.wirez.core.client.ShapeSet;
import org.wirez.core.client.WirezClientManager;
import org.wirez.core.client.factory.ShapeFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Dependent
public class Palette implements IsWidget {

    public interface View extends UberView<Palette> {
        
        View setNoCanvasViewVisible(boolean isVisible);
        
        View setGroupsViewVisible(boolean isVisible);
        
        View addGroup(IsWidget paletteGroupView);

        View clearGroups();
        
        View clear();

    }

    WirezClientManager wirezClientManager;
    Event<PaletteShapeSelectedEvent> paletteShapeSelectedEvent;
    SyncBeanManager beanManager;
    View view;
    
    @Inject
    public Palette(final View view,
                   final WirezClientManager wirezClientManager,
                   final SyncBeanManager beanManager,
                   final Event<PaletteShapeSelectedEvent> paletteShapeSelectedEvent) {
        this.view = view;
        this.wirezClientManager = wirezClientManager;
        this.beanManager = beanManager;
        this.paletteShapeSelectedEvent = paletteShapeSelectedEvent;
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
    
    public void show(final String shapeSetId) {
        clear();
        final ShapeSet wirezShapeSet = getShapeSet(shapeSetId);
        doShow(wirezShapeSet);
        view.setNoCanvasViewVisible(false);
        view.setGroupsViewVisible(true);
    }
    
    private ShapeSet getShapeSet(final String shapeSetId) {
        final Collection<ShapeSet> sets = wirezClientManager.getShapeSets();
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
    
    private void doShow(final ShapeSet wirezShapeSet) {

        // Clear current palette groups.
        view.clearGroups();

        // Load shapes by category.
        final DefinitionSet definitionSet = wirezShapeSet.getDefinitionSet();
        final Collection<ShapeFactory<? extends Definition, ? extends Shape>> factories = wirezShapeSet.getFactories();
        final Collection<Definition> definitions = definitionSet.getDefinitions();
        final Map<String, Collection<ShapeFactory<? extends Definition, ? extends Shape>>>
                groupFactories = new HashMap<String, Collection<ShapeFactory<? extends Definition, ? extends Shape>>>();
        for (final ShapeFactory<? extends Definition, ? extends Shape> factory : factories) {
            for (final Definition definition : definitions) {
                if (factory.accepts(definition)) {
                    final String category = definition.getContent().getCategory();
                    if (groupFactories.get(category) == null)  {
                        groupFactories.put(category, new LinkedList<ShapeFactory<? extends Definition, ? extends Shape>>());
                    }
                    groupFactories.get(category).add(factory);
                    break;
                }
            }
            
        }
        
        // Show a palette group for each category.
        for (final Map.Entry<String, Collection<ShapeFactory<? extends Definition, ? extends Shape>>> entry : groupFactories.entrySet()) {
            final String category = entry.getKey();
            final Collection<ShapeFactory<? extends Definition, ? extends Shape>> _factories = entry.getValue();

            final PaletteGroup paletteGroup = buildPaletteGroup();
            paletteGroup.setSize(350, 100);
            paletteGroup.setHeader(category);
            view.addGroup(paletteGroup);

            for (final ShapeFactory<? extends Definition, ? extends Shape> _factory : _factories) {
                final String description = _factory.getDescription();
                final ShapeGlyph glyph = _factory.getGlyph();
                paletteGroup.addGlyph(description, glyph, new Command() {
                    @Override
                    public void execute() {
                        GWT.log("Palette: Adding " + description);
                        paletteShapeSelectedEvent.fire(new PaletteShapeSelectedEvent(_factory));
                    }
                });
            }
        }
        
    }
    
    private PaletteGroup buildPaletteGroup() {
        return beanManager.lookupBean( PaletteGroup.class ).newInstance();
    }
    
}
