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
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import org.gwtbootstrap3.client.ui.PanelCollapse;
import org.gwtbootstrap3.client.ui.PanelGroup;
import org.gwtbootstrap3.client.ui.PanelHeader;

public class PaletteGroupView extends Composite implements PaletteGroup.View {

    interface ViewBinder extends UiBinder<Widget, PaletteGroupView> {

    }

    private static ViewBinder uiBinder = GWT.create( ViewBinder.class );
    
    @UiField
    PanelGroup accordion;

    @UiField
    PanelHeader panelHeader;

    @UiField
    PanelCollapse panelCollapse;
    
    @UiField
    SimplePanel panelShapes;

    private final LienzoPanel lienzoPanel = new LienzoPanel(400, 400);
    private final Layer lienzoLayer = new Layer();
    // final Tooltip tooltip = new Tooltip();
    
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
        lienzoPanel.add(lienzoLayer);
        // lienzoLayer.add(tooltip);
        panelShapes.add(lienzoPanel);
    }

    @Override
    public PaletteGroup.View setSize(double width, double height) {
        lienzoPanel.setSize(width + "px", height + "px");
        return this;
    }

    @Override
    public PaletteGroup.View setHeader(final String title) {
        panelHeader.setText(title);
        panelHeader.setTitle(title);
        return this;
    }

    @Override
    public PaletteGroup.View addGlyph(final IPrimitive glyphView, 
                                      final double x, final double y,
                                      final PaletteGroup.GlyphViewCallback callback) {
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
        
        glyphView.addNodeMouseMoveHandler(new NodeMouseMoveHandler() {
            @Override
            public void onNodeMouseMove(final NodeMouseMoveEvent nodeMouseMoveEvent) {
                callback.onNodeMouseMove(glyphView.getX(), glyphView.getY());
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
