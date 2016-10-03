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

package org.kie.workbench.common.stunner.core.client.canvas.controls.event;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.client.shape.Shape;
import org.kie.workbench.common.stunner.core.client.shape.factory.ShapeFactory;

/**
 * Event for requesting the canvas builder control to add a new shape. 
 */
@NonPortable
public final class BuildCanvasShapeEvent extends AbstractCanvasHandlerEvent<AbstractCanvasHandler> {

    private Object definition;
    private ShapeFactory<?, ?, ? extends Shape> shapeFactory;
    private double x;
    private double y;

    public BuildCanvasShapeEvent(final AbstractCanvasHandler abstractCanvasHandler,
                                 final Object definition,
                                 final ShapeFactory<?, ?, ? extends Shape> shapeFactory) {
        super( abstractCanvasHandler );
        this.definition = definition;
        this.shapeFactory = shapeFactory;
        this.x = -1;
        this.y = -1;
    }

    public BuildCanvasShapeEvent(final AbstractCanvasHandler abstractCanvasHandler,
                                 final Object definition,
                                 final ShapeFactory<?, ?, ? extends Shape> shapeFactory,
                                 final double x,
                                 final double y) {
        super( abstractCanvasHandler );
        this.definition = definition;
        this.shapeFactory = shapeFactory;
        this.x = x;
        this.y = y;
    }

    public Object getDefinition() {
        return definition;
    }

    public ShapeFactory<?, ?, ? extends Shape> getShapeFactory() {
        return shapeFactory;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "PaletteShapeSelectedEvent [definition=" + definition + ", factory=" + shapeFactory.toString() + 
                ", x=" + x + ", y=" + y + "]";
    }

}
