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
package org.wirez.core.client.factory;

import org.wirez.core.client.Shape;
import org.wirez.core.client.canvas.CanvasHandler;

/**
 * Factory for building shapes available for authoring.
 */
public interface ShapeFactory<W, S extends Shape<W>> {

    /**
     * Does the Factory builds the given Wirez definition.
     */
    boolean accepts(final Object definition);
    
    /**
     * Get the glyph factory for this shape. Glyphs are used to represent the Shape in several 
     * parts on the application, such as the Palette or the Explorer.
     */
    ShapeGlyphFactory getGlyphFactory();

    /**
     * Get description of Shape.
     */
    String getDescription();

    /**
     * Get a Shape to be created on the Canvas.
     */
    S build(W definition, CanvasHandler canvasHandler);

}
