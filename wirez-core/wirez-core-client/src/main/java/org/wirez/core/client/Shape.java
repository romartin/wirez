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

package org.wirez.core.client;

import com.ait.lienzo.client.core.shape.IContainer;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Node;
import org.wirez.core.api.definition.Definition;

public interface Shape<W extends Definition> {
    
    /**
     * Get the identifier for the Shape.
     * @return The identifier for Shape
     */
    String getId();

    /**
     * Set the identifier for the shape.
     */
    Shape<W> setId(String id);

    /**
     * The lienzo shape. All built-in handlers and controls provided by wirez will be attached to it and will mutate it.
     * Usually it should return the wires shape's multipath, if it is lienzo wires based.
     */
    com.ait.lienzo.client.core.shape.Shape getShape();

    /**
     * The shape's container, which must determinate, at least, the absolute position in the canvas.
     * Usually it should return the wires shape's container, if it is lienzo wires based.
     */
    Node getShapeContainer();

    /**
     * Destroy the shape and any related components.
     */
    void destroy();

    
    
}
