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
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Line;
import com.ait.lienzo.client.core.shape.wires.*;
import com.ait.lienzo.shared.core.types.ColorName;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import org.wirez.client.shapes.AbstractWiresConnectorView;
import org.wirez.client.shapes.AbstractWiresShapeView;
import org.wirez.core.client.canvas.wires.WiresCanvas;
import org.wirez.core.client.view.ShapeView;

import javax.annotation.PostConstruct;

/**
 * This is the root Canvas provided by Wirez
 */
public class CanvasView extends Composite implements WiresCanvas.View {

    private FlowPanel mainPanel = new FlowPanel();
    private FlowPanel toolsPanel = new FlowPanel();
    private FocusableLienzoPanel panel;
    protected Layer canvasLayer = new Layer();
    protected WiresManager wiresManager;
    protected org.wirez.core.client.canvas.Layer layer;

    @PostConstruct
    public void init() {
        initWidget( mainPanel );
        wiresManager = WiresManager.get(canvasLayer);
    }

    @Override
    public WiresCanvas.View init(final org.wirez.core.client.canvas.Layer layer) {
        this.layer = layer;
        layer.initialize( canvasLayer );
        return this;
    }

    @Override
    public Canvas.View show(final int width, final int height, final int padding) {

        panel = new FocusableLienzoPanel( width + padding, height + padding );
        mainPanel.add(toolsPanel);
        mainPanel.add(panel);

        //Grid...
        Line line1 = new Line( 0,
                0,
                0,
                0 ).setStrokeColor( ColorName.BLUE ).setAlpha( 0.2 ); // primary lines
        Line line2 = new Line( 0,
                0,
                0,
                0 ).setStrokeColor( ColorName.GREEN ).setAlpha( 0.2 ); // secondary dashed-lines
        line2.setDashArray( 2,
                2 );

        GridLayer gridLayer = new GridLayer( 100,
                line1,
                25,
                line2 );
        panel.setBackgroundLayer( gridLayer );

        panel.getScene().add( canvasLayer );
        
        return this;
    }

    @Override
    public Canvas.View add(final IsWidget widget) {
        toolsPanel.add(widget);
        return this;
    }

    @Override
    public WiresCanvas.View addShape(final ShapeView<?> shapeView) {
        if ( shapeView instanceof AbstractWiresShapeView) {
            WiresShape wiresShape = (WiresShape) shapeView;
            wiresManager.createMagnets(wiresShape);
            wiresManager.registerShape(wiresShape);
        } else if (shapeView instanceof AbstractWiresConnectorView) {
            WiresConnector wiresConnector = (WiresConnector) shapeView;
            wiresManager.registerConnector(wiresConnector);
        } else {
            wiresManager.getLayer().getLayer().add((IPrimitive<?>) shapeView);
        }
        return this;
    }

    @Override
    public WiresCanvas.View removeShape(final ShapeView<?> shapeView) {
        if ( shapeView instanceof AbstractWiresShapeView ) {
            WiresShape wiresShape = (WiresShape) shapeView;
            wiresManager.deregisterShape(wiresShape);
        } else if (shapeView instanceof AbstractWiresConnectorView) {
            WiresConnector wiresConnector = (WiresConnector) shapeView;
            wiresManager.deregisterConnector(wiresConnector);
        } else {
            wiresManager.getLayer().getLayer().remove((IPrimitive<?>) shapeView);
        }
        return this;
    }

    @Override
    public WiresCanvas.View addChildShape(final ShapeView<?> parent, final ShapeView<?> child) {
        final WiresShape parentShape = (WiresShape) parent;
        final WiresShape childShape = (WiresShape) child;
        parentShape.add( childShape );
        return this;
    }

    @Override
    public WiresCanvas.View removeChildShape(final ShapeView<?> parent, final ShapeView<?> child) {
        final WiresShape parentShape = (WiresShape) parent;
        final WiresShape childShape = (WiresShape) child;
        parentShape.remove( childShape );
        return this;
    }

    @Override
    public WiresCanvas.View setConnectionAcceptor(final IConnectionAcceptor connectionAcceptor) {
        wiresManager.setConnectionAcceptor(connectionAcceptor);
        return this;
    }

    @Override
    public WiresCanvas.View setContainmentAcceptor(final IContainmentAcceptor containmentAcceptor) {
        wiresManager.setContainmentAcceptor(containmentAcceptor);
        return this;
    }

    @Override
    public org.wirez.core.client.canvas.Layer getLayer() {
        return layer;
    }

    @Override
    public WiresManager getWiresManager() {
        return wiresManager;
    }

    @Override
    public Canvas.View clear() {
        canvasLayer.clear();
        return this;
    }
}
