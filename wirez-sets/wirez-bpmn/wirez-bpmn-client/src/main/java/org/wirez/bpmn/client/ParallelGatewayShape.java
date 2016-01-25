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

package org.wirez.bpmn.client;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.RegularPolygon;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.shared.core.types.ColorName;
import org.wirez.bpmn.api.ParallelGateway;
import org.wirez.bpmn.api.property.Radius;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.util.ElementUtils;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.mutation.HasRadiusMutation;
import org.wirez.core.client.mutation.MutationContext;

import java.util.ArrayList;
import java.util.Collection;

public class ParallelGatewayShape extends BPMNBasicShape<ParallelGateway> implements HasRadiusMutation {

    protected RegularPolygon polygon;

    public ParallelGatewayShape(Group group, WiresManager manager) {
        super(new MultiPath().rect(0, 0, ParallelGateway.RADIUS * 2, ParallelGateway.RADIUS * 2)
                .setFillAlpha(0)
                .setStrokeAlpha(0), 
                group, manager);
        init();
    }

    @Override
    public Collection<Shape> getDecorators() {
        return new ArrayList<Shape>() {{
            add( polygon );
        }};
    }

    @Override
    protected Shape getShape() {
        return polygon;
    }

    protected void init() {
        final int radius = ParallelGateway.RADIUS;
        polygon = new RegularPolygon(4, radius)
            .setX(radius)
            .setY(radius)
            .setStrokeWidth(0)
            .setStrokeAlpha(0)
            .setFillColor(ParallelGateway.COLOR)
            .setFillAlpha(0.50)
            .setStrokeColor(ColorName.BLACK);

        this.addChild(polygon, WiresLayoutContainer.Layout.CENTER);
    }

    public void applyElementProperties(Node<ViewContent<ParallelGateway>, Edge> element, CanvasHandler wirezCanvas, MutationContext mutationContext) {
        super.applyElementProperties(element, wirezCanvas, mutationContext);

        // Radius.
        _applyRadius(element, mutationContext);
        
    }

    protected ParallelGatewayShape _applyRadius(final Node<ViewContent<ParallelGateway>, Edge> element, final MutationContext mutationContext) {
        final Radius radiusProperty  = (Radius) ElementUtils.getProperty(element, Radius.ID);
        final Integer radius = radiusProperty.getValue();
        if ( null != radius ) {
            applyRadius(radius, mutationContext);
        }
        return this;
    }

    @Override
    public void applyRadius(final double radius, final MutationContext mutationContext) {
        if (radius > 0) {
            _applyParallelGatewayRadius(polygon, radius);
        }
    }

    protected ParallelGatewayShape _applyParallelGatewayRadius(final RegularPolygon polygon, final double radius) {
        polygon.setRadius(radius);
        this.moveChild(polygon.getID(), radius, radius);
        return this;
    }


    @Override
    public String toString() {
        return "ParallelGatewayShape{}";
    }
    
}
