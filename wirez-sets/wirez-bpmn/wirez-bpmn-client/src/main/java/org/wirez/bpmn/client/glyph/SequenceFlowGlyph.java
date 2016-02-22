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

import com.ait.lienzo.client.core.shape.Arrow;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.shared.core.types.ArrowType;
import org.wirez.bpmn.api.SequenceFlow;
import org.wirez.core.client.view.ShapeGlyph;

public class SequenceFlowGlyph implements ShapeGlyph {

    public static final SequenceFlowGlyph INSTANCE = new SequenceFlowGlyph();
    private Group group = new Group();

    public SequenceFlowGlyph() {
        final Arrow arrow = new Arrow(new Point2D(0, 50), new Point2D(50, 0), 5, 10, 45, 45, ArrowType.AT_END)
                .setStrokeWidth(5).setStrokeColor(SequenceFlow.COLOR).setDraggable(true);
        group.add(arrow);
    }

    @Override
    public Group getGroup() {
        return group;
    }

    @Override
    public double getWidth() {
        return 50;
    }

    @Override
    public double getHeight() {
        return 50;
    }
}
