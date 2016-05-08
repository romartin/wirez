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

package org.wirez.client.shapes.view.glyph;

import com.ait.lienzo.client.core.shape.Rectangle;
import org.wirez.client.lienzo.util.LienzoShapeUtils;
import org.wirez.core.client.util.ShapeUtils;

public class WiresRectangleGlyph extends AbstractGlyph {

    private static final double WIDTH = 50;
    private static final double HEIGHT = 50;

    public WiresRectangleGlyph() {
        this(WIDTH, HEIGHT, "#000000");
    }
    
    public WiresRectangleGlyph(final double width, final double height, final String color) {
        super(width, height);
        build(width, height, color);
    }
    
    
    private void build(final double width, final double height, final String color) {
        group.removeAll();
        final Rectangle rectangle = new Rectangle( width, height )
                .setStrokeWidth( 0.5 )
                .setFillGradient( LienzoShapeUtils.getLinearGradient(color, "#FFFFFF", WIDTH, HEIGHT) );
        group.add(rectangle);
    }

}
