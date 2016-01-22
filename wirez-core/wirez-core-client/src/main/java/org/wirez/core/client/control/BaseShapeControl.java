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

package org.wirez.core.client.control;

import org.wirez.core.api.graph.Element;
import org.wirez.core.client.Shape;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.canvas.command.impl.DefaultCanvasCommands;

import javax.inject.Inject;

public abstract class BaseShapeControl<S extends Shape, E extends Element> implements ShapeControl<S, E>, HasCanvasHandler {
    
    protected CanvasHandler canvasHandler;
    protected DefaultCanvasCommands defaultCanvasCommands;

    @Inject
    public BaseShapeControl(DefaultCanvasCommands defaultCanvasCommands) {
        this.defaultCanvasCommands = defaultCanvasCommands;
    }

    @Override
    public void setCanvasHandler(final CanvasHandler canvasHandler) {
        this.canvasHandler = canvasHandler;
    }

    protected double[] getContainerXY(final Shape shape) {
        return new double[] { shape.getShapeNode().getAttributes().getX(),
                shape.getShapeNode().getAttributes().getY()};
    }
    
    protected CanvasCommandManager getCommandManager() {
        return (CanvasCommandManager) canvasHandler;
    }
    
}
