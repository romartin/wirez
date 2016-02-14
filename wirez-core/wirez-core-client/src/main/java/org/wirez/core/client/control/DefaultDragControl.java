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

import com.ait.lienzo.client.core.shape.wires.event.AbstractWiresEvent;
import com.ait.lienzo.client.core.shape.wires.event.DragEvent;
import com.ait.lienzo.client.core.shape.wires.event.DragHandler;
import com.google.gwt.core.client.GWT;
import org.wirez.core.api.graph.Element;
import org.wirez.core.client.Shape;
import org.wirez.core.client.canvas.command.impl.DefaultCanvasCommands;
import org.wirez.core.client.canvas.command.impl.MoveCanvasElementCommand;
import org.wirez.core.client.impl.BaseShape;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultDragControl extends BaseDragControl<Shape, Element>  {

    @Inject
    public DefaultDragControl(DefaultCanvasCommands defaultCanvasCommands) {
        super(defaultCanvasCommands);
    }

    @Override
    public void enable(final Shape shape, final Element element) {
        
        if (shape instanceof BaseShape) {
            ( (BaseShape) shape).setDraggable(true).addWiresHandler(AbstractWiresEvent.DRAG, new DragHandler() {
                @Override
                public void onDragStart(DragEvent dragEvent) {
                    
                }

                @Override
                public void onDragMove(DragEvent dragEvent) {

                }

                @Override
                public void onDragEnd(DragEvent dragEvent) {
                    final double[] xy = getContainerXY(shape);
                    getCommandManager().execute( defaultCanvasCommands.MOVE(element, xy[0], xy[1]) );
                }
            });
        }
        
    }

    protected double[] getContainerXY(final Shape shape) {
        return new double[] { shape.getShapeContainer().getAttributes().getX(),
                shape.getShapeContainer().getAttributes().getY()};
    }
    
    @Override
    public void disable(final Shape shape) {

        if (shape instanceof BaseShape) {
            ((BaseShape) shape).setDraggable(false);
        }
        
    }

}
