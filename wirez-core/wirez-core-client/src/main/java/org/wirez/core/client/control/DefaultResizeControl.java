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
import com.ait.lienzo.client.core.shape.wires.event.ResizeEvent;
import com.ait.lienzo.client.core.shape.wires.event.ResizeHandler;
import org.wirez.core.api.graph.impl.ViewElement;
import org.wirez.core.client.Shape;
import org.wirez.core.client.canvas.command.impl.SetCanvasElementSizeCommand;
import org.wirez.core.client.impl.BaseShape;
import org.wirez.core.client.mutation.HasSizeMutation;
import org.wirez.core.client.mutation.StaticMutationContext;

public class DefaultResizeControl<S extends Shape, E extends ViewElement> extends BaseShapeControl<S, E> {
    
    @Override
    public void enable(final S shape, final E element) {
        
        if (shape instanceof BaseShape) {
            ( (BaseShape) shape).setResizable(true).addWiresHandler(AbstractWiresEvent.RESIZE, new ResizeHandler() {
                @Override
                public void onResizeStart(ResizeEvent resizeEvent) {
                    doResizeStart(shape, element, resizeEvent.getWidth(), resizeEvent.getHeight());
                }

                @Override
                public void onResizeStep(ResizeEvent resizeEvent) {
                    doResizeStep(shape, element, resizeEvent.getWidth(), resizeEvent.getHeight());
                }

                @Override
                public void onResizeEnd(ResizeEvent resizeEvent) {
                    doResizeEnd(shape, element, resizeEvent.getWidth(), resizeEvent.getHeight());
                }
            });
        }
        
    }
    
    protected void doResizeStart(final S shape, final E element, final double width, final double height) {
        
    }

    protected void doResizeStep(final S shape, final E element, final double width, final double height) {
        if (shape instanceof HasSizeMutation) {
            ( (HasSizeMutation) shape).applySize(width, height, new StaticMutationContext());
        }
    }
    
    protected void doResizeEnd(final S shape, final E element, final double width, final double height) {
        getCommandManager().execute(new SetCanvasElementSizeCommand(element, width, height));
    }

    @Override
    public void disable(final S shape) {

        if (shape instanceof BaseShape) {
            ((BaseShape) shape).setResizable(false);
        }
        
    }

}
