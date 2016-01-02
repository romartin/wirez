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

package org.wirez.client.widgets.palette.accordion.group;

import com.ait.lienzo.client.core.event.*;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import org.gwtbootstrap3.client.ui.Anchor;
import org.gwtbootstrap3.client.ui.PanelCollapse;
import org.gwtbootstrap3.client.ui.PanelGroup;
import org.gwtbootstrap3.client.ui.PanelHeader;
import org.wirez.core.api.util.UUID;

public class PaletteGroupView extends Composite implements PaletteGroup.View {

    interface ViewBinder extends UiBinder<Widget, PaletteGroupView> {

    }

    private static ViewBinder uiBinder = GWT.create( ViewBinder.class );
    
    @UiField
    PanelGroup accordion;

    @UiField
    PanelHeader panelHeader;

    @UiField
    Anchor anchor;
    
    @UiField
    PanelCollapse panelCollapse;
    
    @UiField
    SimplePanel panelShapes;

    private LienzoPanel lienzoPanel;
    private final Layer lienzoLayer = new Layer();
    
    final Timer timer = new Timer() {
        @Override
        public void run() {
            // tooltip.hide();
        }
    };
    
    PaletteGroup presenter;

    @Override
    public void init(final PaletteGroup presenter) {
        this.presenter = presenter;
        initWidget( uiBinder.createAndBindUi( this ) );
        lienzoLayer.setTransformable(true);
        
        // Bootstrap accordion setup.
        final String accId = UUID.uuid();
        accordion.setId(accId);
        final String collapseId = UUID.uuid();
        panelCollapse.setId(collapseId);
        anchor.setDataParent("#" + accId);
        anchor.setDataTarget("#" + collapseId);
    }

    @Override
    public PaletteGroup.View setSize(final double width, final double height) {
        initLienzoPanel( (int)width, (int) height);
        return this;
    }
    
    private void initLienzoPanel(final int width, final int height) {
        lienzoPanel = new LienzoPanel(width, height);
        lienzoPanel.add(lienzoLayer);
        // lienzoLayer.add(tooltip);
        panelShapes.add(lienzoPanel);
    }

    @Override
    public PaletteGroup.View setHeader(final String title) {
        anchor.setText(title);
        anchor.setTitle(title);
        return this;
    }

    @Override
    public PaletteGroup.View addGlyph(final IPrimitive glyphView, 
                                      final double x, final double y,
                                      final PaletteGroup.GlyphViewCallback callback) {
        assert lienzoPanel != null;
        
        GWT.log("PaletteGroupView#addGlyph");
        glyphView.setX(x);
        glyphView.setY(y);
        
        glyphView.addNodeMouseClickHandler(new NodeMouseClickHandler() {
            @Override
            public void onNodeMouseClick(final NodeMouseClickEvent nodeMouseClickEvent) {
                callback.onClick();
            }
        });
        glyphView.addNodeMouseDoubleClickHandler(new NodeMouseDoubleClickHandler() {
            @Override
            public void onNodeMouseDoubleClick(final NodeMouseDoubleClickEvent nodeMouseDoubleClickEvent) {
                callback.onClick();
            }
        });
        
        lienzoLayer.add(glyphView);
        
        glyphView.addNodeMouseDownHandler(new NodeMouseDownHandler() {
            @Override
            public void onNodeMouseDown(NodeMouseDownEvent event) {
                callback.onMouseDown(lienzoPanel, event.getX(), event.getY());
            }
        });
        
        glyphView.addNodeMouseMoveHandler(new NodeMouseMoveHandler() {
            @Override
            public void onNodeMouseMove(final NodeMouseMoveEvent nodeMouseMoveEvent) {
                callback.onMouseMove(lienzoPanel, glyphView.getX(), glyphView.getY());
            }
        });
        
        return this;
    }

    @Override
    public PaletteGroup.View showTooltip(final String title, 
                                         final double x, 
                                         final double y, 
                                         final int duration) {
        /*if (!timer.isRunning()) {
            tooltip.setX(x).setY(y);
            tooltip.show(title, "");
            timer.schedule(duration);
        }*/
        return this;
    }

    @Override
    public PaletteGroup.View clear() {
        setHeader("");
        lienzoLayer.clear();
        return this;
    }

}
