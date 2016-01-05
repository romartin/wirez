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
import org.wirez.core.client.canvas.command.impl.MoveCanvasElementCommand;
import org.wirez.core.client.impl.BaseShape;

public class DefaultDragControl<S extends Shape, E extends Element> extends BaseShapeControl<S, E>  {

    @Override
    public void enable(final S shape, final E element) {
        
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
                    GWT.log("DragControl#onDragEnd [node=" + element.getUUID() + "]  [x=" + dragEvent.getX() + "] [y=" + dragEvent.getY() + "]");
                    GWT.log("DragControl#onDragEnd [shape=" + shape.getId() + "]  [x=" + xy[0]  + "] [y=" + xy[1] + "]");
                    getCommandManager().execute(new MoveCanvasElementCommand(element, xy[0], xy[1]));
                }
            });
        }
        
    }

    protected double[] getContainerXY(final Shape shape) {
        return new double[] { shape.getShapeNode().getAttributes().getX(),
                shape.getShapeNode().getAttributes().getY()};
    }
    
    @Override
    public void disable(final S shape) {

        if (shape instanceof BaseShape) {
            ((BaseShape) shape).setDraggable(false);
        }
        
    }

}
