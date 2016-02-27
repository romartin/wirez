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

import com.ait.lienzo.client.core.shape.Ring;
import org.wirez.bpmn.api.EndTerminateEvent;
import org.wirez.bpmn.client.EndTerminateEventShape;
import org.wirez.client.shapes.glyph.WiresCircleGlyph;

public class EndTerminateEventGlyph extends WiresCircleGlyph {

    public EndTerminateEventGlyph() {
        super();
    }

    public EndTerminateEventGlyph(double radius, String color) {
        super(radius, color);
    }

    @Override
    protected void build(double radius, String color) {
        super.build(radius, color);
        final Double[] ringRadius = EndTerminateEventShape.getRingRadius(radius);
        final Ring ring = new Ring(ringRadius[0], ringRadius[1])
                .setX(radius)
                .setY(radius)
                .setStrokeWidth(0)
                .setFillColor(EndTerminateEvent.RING_COLOR);
        group.add(ring);
        
    }
    
}
