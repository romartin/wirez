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

import com.ait.lienzo.client.core.shape.RegularPolygon;
import org.wirez.client.lienzo.util.LienzoShapeUtils;
import org.wirez.core.client.util.ShapeUtils;

public class WiresPolygonGlyph extends AbstractGlyph {

    private static final double RADIUS = 25;

    public WiresPolygonGlyph() {
        this(RADIUS, "#f0e68c");
    }
    
    public WiresPolygonGlyph(final double radius, final String color) {
        super(radius * 2, radius * 2);
        build(radius, color);
    }
    
    
    private void build(final double radius, final String color) {
        group.removeAll();
        final RegularPolygon polygon = new RegularPolygon( 4, radius )
                .setX( radius )
                .setY( radius )
                .setStrokeWidth( 0.5 )
                .setFillGradient( LienzoShapeUtils.getLinearGradient(color, "#FFFFFF", radius * 2, radius * 2) )
                .setFillAlpha(0.50);

        group.add(polygon);
    }

}
