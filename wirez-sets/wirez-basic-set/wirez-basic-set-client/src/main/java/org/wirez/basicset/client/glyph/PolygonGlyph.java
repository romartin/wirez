/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
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

package org.wirez.basicset.client.glyph;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.RegularPolygon;
import com.ait.lienzo.shared.core.types.ColorName;
import org.wirez.core.client.ShapeGlyph;

public class PolygonGlyph implements ShapeGlyph {

    public static final PolygonGlyph INSTANCE = new PolygonGlyph();
    private static final double WIDTH = 50;
    private static final double HEIGHT = 50;
    private Group group = new Group();

    public PolygonGlyph() {
        
        final RegularPolygon polygon = new RegularPolygon(5, 25)
                .setX(25)
                .setY(25)
                .setStrokeWidth(0)
                .setStrokeAlpha(0)
                .setFillColor(org.wirez.basicset.api.Polygon.COLOR)
                .setFillAlpha(0.50)
                .setStrokeColor(ColorName.BLACK);
        
        group.add(polygon);
    }

    @Override
    public Group getGroup() {
        return group;
    }

    @Override
    public double getWidth() {
        return WIDTH;
    }

    @Override
    public double getHeight() {
        return HEIGHT;
    }
}
