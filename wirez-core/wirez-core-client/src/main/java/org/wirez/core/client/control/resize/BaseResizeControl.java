/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
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

package org.wirez.core.client.control.resize;

import org.wirez.core.api.graph.Element;
import org.wirez.core.client.Shape;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.control.BaseShapeControl;
import org.wirez.core.client.impl.BaseShape;
import org.wirez.core.client.mutation.StaticMutationContext;
import org.wirez.core.client.view.HasRadius;
import org.wirez.core.client.view.HasSize;

public abstract class BaseResizeControl<S extends Shape, E extends Element> extends BaseShapeControl<S, E> {

    public BaseResizeControl(CanvasCommandFactory commandFactory) {
        super(commandFactory);
    }

    protected void doResizeStart(final S shape, final E element, final double width, final double height) {
        
    }

    protected void doResizeStep(final S shape, final E element, final double width, final double height) {
        if (shape.getShapeView() instanceof HasSize) {
            ( (HasSize) shape.getShapeView()).setSize(width, height);
        } else if (shape.getShapeView() instanceof HasRadius) {
            final double radius = getRadius(width, height);
            ( (HasRadius) shape).setRadius(radius);
        }
    }
    
    protected void doResizeEnd(final S shape, final E element, final double width, final double height) {
        
        if (shape.getShapeView() instanceof HasSize) {

            execute( commandFactory.UPDATE_PROPERTY(element, "width", width));
            execute( commandFactory.UPDATE_PROPERTY(element, "height", height));
            
        } else if (shape instanceof HasRadius) {

            final double radius = getRadius(width, height);
            execute( commandFactory.UPDATE_PROPERTY(element, "radius", radius));
            
        }
        
    }

    @Override
    public void doDisable(final S shape) {

        // TODO
        /*if (shape instanceof BaseShape) {
            ((BaseShape) shape).setResizable(false);
        }*/
        
    }

    protected double getRadius(double width, double height) {
        final double radiusW = width / 2;
        final double radiusH = height / 2;
        return  (radiusH >= radiusW ? radiusW : radiusH);
    }

}
