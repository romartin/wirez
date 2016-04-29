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
package org.wirez.client.widgets.canvas.wires;

import com.ait.lienzo.client.core.shape.wires.*;
import org.wirez.client.shapes.AbstractWiresConnectorView;
import org.wirez.client.shapes.AbstractWiresShapeView;
import org.wirez.client.widgets.canvas.CanvasView;
import org.wirez.core.client.canvas.wires.WiresCanvas;
import org.wirez.core.client.shape.view.ShapeView;

/**
 * This is the root Canvas view provided by Lienzo and wires
 */
public class WiresCanvasView extends CanvasView implements org.wirez.core.client.canvas.wires.WiresCanvas.View {

    protected WiresManager wiresManager;

    public void init() {
        super.init();
        wiresManager = WiresManager.get(canvasLayer);
    }

    @Override
    public org.wirez.core.client.canvas.wires.WiresCanvas.View addShape(final ShapeView<?> shapeView) {
        if ( shapeView instanceof AbstractWiresShapeView) {
            WiresShape wiresShape = (WiresShape) shapeView;
            wiresManager.createMagnets(wiresShape);
            wiresManager.registerShape(wiresShape);
        } else if (shapeView instanceof AbstractWiresConnectorView) {
            WiresConnector wiresConnector = (WiresConnector) shapeView;
            wiresManager.registerConnector(wiresConnector);
        } else {
            super.addShape( shapeView );
        }
        return this;
    }

    @Override
    public org.wirez.core.client.canvas.wires.WiresCanvas.View removeShape(final ShapeView<?> shapeView) {
        if ( shapeView instanceof AbstractWiresShapeView ) {
            WiresShape wiresShape = (WiresShape) shapeView;
            wiresManager.deregisterShape(wiresShape);
        } else if (shapeView instanceof AbstractWiresConnectorView) {
            WiresConnector wiresConnector = (WiresConnector) shapeView;
            wiresManager.deregisterConnector(wiresConnector);
        } else {
            super.removeShape( shapeView );
        }
        return this;
    }

    @Override
    public org.wirez.core.client.canvas.wires.WiresCanvas.View setConnectionAcceptor(final IConnectionAcceptor connectionAcceptor) {
        wiresManager.setConnectionAcceptor(connectionAcceptor);
        return this;
    }

    @Override
    public org.wirez.core.client.canvas.wires.WiresCanvas.View setContainmentAcceptor(final IContainmentAcceptor containmentAcceptor) {
        wiresManager.setContainmentAcceptor(containmentAcceptor);
        return this;
    }

    @Override
    public WiresCanvas.View setDockingAcceptor(final IDockingAcceptor dockingAcceptor) {
        wiresManager.setDockingAcceptor(dockingAcceptor);
        return this;
    }

    @Override
    public WiresManager getWiresManager() {
        return wiresManager;
    }

}
