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

package org.wirez.basicset.client;

import com.ait.lienzo.client.core.shape.Circle;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import org.wirez.basicset.api.property.RadiusBuilder;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.impl.ViewElement;
import org.wirez.core.api.graph.impl.ViewNode;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.mutation.HasRadiusMutation;
import org.wirez.core.client.mutation.MutationContext;

import java.util.ArrayList;
import java.util.Collection;

public class CircleShape extends BaseBasicShape<org.wirez.basicset.api.Circle> implements HasRadiusMutation {

    protected Circle circle;
    protected Circle decorator;

    public CircleShape(MultiPath path, Group group, WiresManager manager) {
        super(path, group, manager);
        init();
    }

    @Override
    public Collection<Shape> getDecorators() {
        return new ArrayList<Shape>() {{
            add( decorator );
        }};
    }

    @Override
    public Shape getShape() {
        return circle;
    }

    protected void init() {
        final double radius = org.wirez.basicset.api.Circle.RADIUS;
        circle = new Circle(radius).setX(radius).setY(radius);
        decorator = new Circle(radius).setX(radius).setY(radius).setStrokeWidth(0).setStrokeAlpha(0).setFillAlpha(0);
        this.addChild(decorator, WiresLayoutContainer.Layout.CENTER);
        this.addChild(circle, WiresLayoutContainer.Layout.CENTER);
    }


    @Override
    public void applyElementPosition(ViewNode<org.wirez.basicset.api.Circle, Edge> element, CanvasHandler wirezCanvas, MutationContext mutationContext) {
        super.applyElementPosition(element, wirezCanvas, mutationContext);
    }

    @Override
    public void applyElementSize(ViewNode<org.wirez.basicset.api.Circle, Edge> element, CanvasHandler wirezCanvas, MutationContext mutationContext) {
        super.applyElementSize(element, wirezCanvas, mutationContext);

        // Radius.
        _applyRadius(element, mutationContext);
    }

    @Override
    public void applyElementProperties(ViewNode<org.wirez.basicset.api.Circle, Edge> element, CanvasHandler wirezCanvas, MutationContext mutationContext) {
        super.applyElementProperties(element, wirezCanvas, mutationContext);

        // Radius.
        _applyRadius(element, mutationContext);
    }

    @Override
    public void applyPosition(double x, double y, MutationContext mutationContext) {
        super.applyPosition(x, y, mutationContext);
    }

    @Override
    public void applyRadius(double radius, MutationContext mutationContext) {
        if (radius > 0) {
            _applyCircleRadius(circle, radius);
            _applyCircleRadius(decorator, radius);
        }
    }

    @Override
    protected BaseBasicShape<org.wirez.basicset.api.Circle> _applyFillGradientColor(ViewNode<org.wirez.basicset.api.Circle, Edge> element) {
        _applyFillRadialGradientColor(element);
        return this;
    }

    protected CircleShape _applyRadius(final ViewElement<org.wirez.basicset.api.Circle> element, MutationContext mutationContext) {
        final Integer radius = (Integer) element.getProperties().get(RadiusBuilder.PROPERTY_ID);
        if ( null != radius ) {
            applyRadius(radius, mutationContext);
        }
        return this;
    }

    /*
        Updates the circle X/Y coordinates for the given radius, as the WiresShapes CENTER layout is based on the circle's bounding box.
     */
    protected CircleShape _applyCircleRadius(final Circle circle, final double radius) {
        circle.setRadius(radius);
        this.moveChild(circle.getID(), radius, radius);
        return this;
    }

    @Override
    public String toString() {
        return "CircleShape{}";
    }

    
}
