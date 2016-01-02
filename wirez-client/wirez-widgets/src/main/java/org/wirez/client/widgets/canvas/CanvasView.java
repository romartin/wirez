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
package org.wirez.client.widgets.canvas;

import com.ait.lienzo.client.core.shape.GridLayer;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Line;
import com.ait.lienzo.shared.core.types.ColorName;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;

import javax.annotation.PostConstruct;

/**
 * This is the root Canvas provided by Wirez
 */
public class CanvasView extends Composite implements Canvas.View {

    public static final int DEFAULT_SIZE_WIDTH = 1000;
    public static final int DEFAULT_SIZE_HEIGHT = 1000;

    private FlowPanel mainPanel = new FlowPanel();
    private FlowPanel toolsPanel = new FlowPanel();
    private FocusableLienzoPanel panel;
    protected Layer canvasLayer = new Layer();
    private Canvas presenter;

    @Override
    public void init(final Canvas presenter) {
        this.presenter = presenter;
        presenter.initialize(canvasLayer);
    }
    
    @PostConstruct
    public void init() {
        panel = new FocusableLienzoPanel( DEFAULT_SIZE_WIDTH,
                                          DEFAULT_SIZE_HEIGHT );

        mainPanel.add(toolsPanel);
        mainPanel.add(panel);
        initWidget( mainPanel );

        //Grid...
        Line line1 = new Line( 0,
                               0,
                               0,
                               0 ).setStrokeColor( ColorName.BLUE ).setAlpha( 0.5 ); // primary lines
        Line line2 = new Line( 0,
                               0,
                               0,
                               0 ).setStrokeColor( ColorName.GREEN ).setAlpha( 0.5 ); // secondary dashed-lines
        line2.setDashArray( 2,
                            2 );
    
        GridLayer gridLayer = new GridLayer( 100,
                                             line1,
                                             25,
                                             line2 );
        panel.setBackgroundLayer( gridLayer );

        panel.getScene().add( canvasLayer );
    }

    @Override
    public Canvas.View add(final IsWidget widget) {
        toolsPanel.add(widget);
        return this;
    }

    @Override
    public Canvas.View clear() {
        canvasLayer.clear();
        return this;
    }
}
