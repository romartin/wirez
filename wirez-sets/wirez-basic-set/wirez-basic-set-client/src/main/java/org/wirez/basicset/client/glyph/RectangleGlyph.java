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

package org.wirez.basicset.client.glyph;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.Rectangle;
import org.wirez.core.client.ShapeGlyph;

public class RectangleGlyph implements ShapeGlyph {

    public static final RectangleGlyph INSTANCE = new RectangleGlyph();
    private static final double WIDTH = 50;
    private static final double HEIGHT = 50;
    private Group group = new Group();

    public RectangleGlyph() {
        final Rectangle rectangle = new Rectangle(WIDTH, 50).setFillColor(org.wirez.basicset.api.Rectangle.COLOR);
        group.add(rectangle);
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
        return 50;
    }
}
