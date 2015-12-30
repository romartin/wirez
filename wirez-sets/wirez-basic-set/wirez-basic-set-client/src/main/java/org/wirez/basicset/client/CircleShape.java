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

import com.ait.lienzo.client.core.shape.*;
import com.ait.lienzo.client.core.shape.Circle;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import org.wirez.basicset.api.property.RadiusBuilder;
import org.wirez.core.api.graph.*;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.impl.ViewElement;
import org.wirez.core.api.graph.impl.ViewNode;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.mutation.MutationContext;

import java.util.ArrayList;
import java.util.Collection;

public class CircleShape extends BaseBasicShape<org.wirez.basicset.api.Circle> {

    private static final double RADIUS = 50;

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
        circle = new Circle(RADIUS).setRadius(25).setX(25).setY(25);
        decorator = new Circle(RADIUS).setRadius(25).setX(25).setY(25).setStrokeWidth(0).setStrokeAlpha(0).setFillAlpha(0);
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
        _applyRadius(element);
    }

    @Override
    public void applyPosition(double x, double y, MutationContext mutationContext) {
        super.applyPosition(x, y, mutationContext);
    }


    public void setRadius(final double radius) {
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

    protected CircleShape _applyRadius(final ViewElement<org.wirez.basicset.api.Circle> element) {
        final Integer radius = (Integer) element.getProperties().get(RadiusBuilder.PROPERTY_ID);
        if ( null != radius ) {
            setRadius(radius);
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
