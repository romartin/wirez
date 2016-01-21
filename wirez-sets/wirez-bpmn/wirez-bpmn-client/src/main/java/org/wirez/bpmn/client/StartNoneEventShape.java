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

package org.wirez.bpmn.client;

import com.ait.lienzo.client.core.shape.Circle;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import org.wirez.bpmn.api.StartNoneEvent;
import org.wirez.bpmn.api.property.Radius;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.util.PropertyUtils;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.mutation.HasRadiusMutation;
import org.wirez.core.client.mutation.MutationContext;

import java.util.ArrayList;
import java.util.Collection;

public class StartNoneEventShape extends BPMNBasicShape<StartNoneEvent> implements HasRadiusMutation {

    protected Circle circle;
    
    public StartNoneEventShape(Group group, WiresManager manager) {
        super(new MultiPath().rect(0,0, StartNoneEvent.RADIUS * 2, StartNoneEvent.RADIUS * 2)
                .setFillAlpha(0)
                .setStrokeAlpha(0), 
                group, manager);
        init();
    }

    protected void init() {
        final double radius = StartNoneEvent.RADIUS;
        circle = new Circle(radius).setX(radius).setY(radius);
        this.addChild(circle, WiresLayoutContainer.Layout.CENTER);
    }


    @Override
    protected Shape getShape() {
        return circle;
    }

    @Override
    public Collection<Shape> getDecorators() {
        return new ArrayList<Shape>() {{
            add( circle );
        }};
    }
    
    @Override
    public void applyElementProperties(Node<ViewContent<StartNoneEvent>, Edge> element, CanvasHandler wirezCanvas, MutationContext mutationContext) {
        super.applyElementProperties(element, wirezCanvas, mutationContext);

        // Radius.
        _applyRadius(element, mutationContext);
    }

    @Override
    public void applyRadius(double radius, MutationContext mutationContext) {
        if (radius > 0) {
            circle.setRadius(radius);
            this.moveChild(circle.getID(), radius, radius);
        }
    }

    protected StartNoneEventShape _applyRadius(final Node<ViewContent<StartNoneEvent>, Edge> element, MutationContext mutationContext) {
        final Radius radiusProperty  = (Radius) PropertyUtils.getProperty(element.getProperties(), Radius.ID);
        final Integer radius = radiusProperty.getValue();
        if ( null != radius ) {
            applyRadius(radius, mutationContext);
        }
        return this;
    }

    @Override
    public String toString() {
        return "CircleShape{}";
    }

    
}
