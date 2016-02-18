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

/**
 * A shape control. It can implement <code>IsWidet</code> if the control have to include views outside the canvas.
 * @param <S> The shape targeted for the control 
 * @param <E> The view element that the control handles.
 */
public interface ShapeControl<C extends CanvasHandler, S extends Shape, E extends Element> {
    
    void enable(C canvasHandler, S shape, E element);

    void disable(C canvasHandler, S shape);
    
}
