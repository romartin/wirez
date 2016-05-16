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

import com.ait.lienzo.client.core.shape.Circle;
import org.wirez.client.lienzo.util.LienzoShapeUtils;

public class CircleGlyph extends AbstractGlyph {

    private static final double RADIUS = 25;

    public CircleGlyph() {
        this(RADIUS, "#3eb870");
    }
    
    public CircleGlyph(final double radius, final String color) {
        super(radius * 2, radius * 2);
        build(radius, color);
    }
    
    
    protected void build(final double radius, final String color) {
        group.removeAll();
        final Circle circle = new Circle( radius )
                .setX( radius )
                .setY( radius )
                .setStrokeWidth( 0.5 )
                .setFillGradient( LienzoShapeUtils.getLinearGradient(color, "#FFFFFF", radius* 2, radius * 2) );
        group.add(circle);
    }

}
