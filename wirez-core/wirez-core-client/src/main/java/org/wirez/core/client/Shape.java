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
     * The main shape's node. All handlers and controls provided by wirez will be attached to this node. 
     * Usually it returns the parent Group of all shapes that conform this wirez shape.
     */
    Node getShapeNode();

    /**
     * Destroy the shape and any related components.
     */
    void destroy();

    
    
}
