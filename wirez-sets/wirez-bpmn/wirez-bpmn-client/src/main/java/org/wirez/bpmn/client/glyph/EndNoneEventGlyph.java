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

package org.wirez.bpmn.client.glyph;

import com.ait.lienzo.client.core.shape.Circle;
import com.ait.lienzo.client.core.shape.Group;
import org.wirez.bpmn.api.EndNoneEvent;
import org.wirez.core.client.util.ShapeUtils;
import org.wirez.core.client.view.ShapeGlyph;

public class EndNoneEventGlyph implements ShapeGlyph {

    public static final EndNoneEventGlyph INSTANCE = new EndNoneEventGlyph();
    private static final double RADIUS = 25;
    private Group group = new Group();

    public EndNoneEventGlyph() {
        final Circle circle = new Circle(RADIUS)
                .setX(RADIUS)
                .setY(RADIUS)
                .setStrokeWidth(0.5)
                .setFillGradient(ShapeUtils.getLinearGradient(EndNoneEvent.COLOR, "#FFFFFF", RADIUS * 2, RADIUS * 2));
        group.add(circle);
    }

    @Override
    public Group getGroup() {
        return group;
    }

    @Override
    public double getWidth() {
        return RADIUS * 2;
    }

    @Override
    public double getHeight() {
        return RADIUS * 2;
    }
}
