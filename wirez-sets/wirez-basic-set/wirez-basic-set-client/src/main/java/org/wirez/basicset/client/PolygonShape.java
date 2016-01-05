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

package org.wirez.basicset.client;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.RegularPolygon;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.shared.core.types.ColorName;
import org.wirez.basicset.api.Polygon;
import org.wirez.basicset.api.property.RadiusBuilder;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.mutation.HasRadiusMutation;
import org.wirez.core.client.mutation.MutationContext;

import java.util.ArrayList;
import java.util.Collection;

public class PolygonShape extends BaseBasicShape<org.wirez.basicset.api.Polygon> implements HasRadiusMutation {

    private static final int SIZE = 5;
    
    protected RegularPolygon polygon;
    protected RegularPolygon decorator;

    public PolygonShape(MultiPath path, Group group, WiresManager manager) {
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
        return polygon;
    }

    protected void init() {

        polygon = new RegularPolygon(SIZE, org.wirez.basicset.api.Polygon.RADIUS)
            .setX(25)
            .setY(25)
            .setStrokeWidth(0)
            .setStrokeAlpha(0)
            .setFillColor(org.wirez.basicset.api.Polygon.COLOR)
            .setFillAlpha(0.50)
            .setStrokeColor(ColorName.BLACK);

        decorator = new RegularPolygon(SIZE, org.wirez.basicset.api.Polygon.RADIUS)
                .setX(25)
                .setY(25)
                .setStrokeWidth(0)
                .setStrokeAlpha(0)
                .setFillColor(org.wirez.basicset.api.Polygon.COLOR)
                .setFillAlpha(0)
                .setStrokeColor(ColorName.BLACK);

        this.addChild(decorator, WiresLayoutContainer.Layout.CENTER);
        this.addChild(polygon, WiresLayoutContainer.Layout.CENTER);
    }

    @Override
    public void applyElementSize(Node<ViewContent<Polygon>, Edge> element, CanvasHandler wirezCanvas, MutationContext mutationContext) {
        super.applyElementSize(element, wirezCanvas, mutationContext);

        // Radius.
        _applyRadius(element, mutationContext);
    }

    @Override
    public void applyElementProperties(Node<ViewContent<Polygon>, Edge> element, CanvasHandler wirezCanvas, MutationContext mutationContext) {
        super.applyElementProperties(element, wirezCanvas, mutationContext);

        // Radius.
        _applyRadius(element, mutationContext);
    }

    protected PolygonShape _applyRadius(final Node<ViewContent<Polygon>, Edge> element, final MutationContext mutationContext) {
        final Integer radius = (Integer) element.getProperties().get(RadiusBuilder.PROPERTY_ID);
        if ( null != radius ) {
            applyRadius(radius, mutationContext);
        }
        return this;
    }

    protected PolygonShape _applyPolygonRadius(final com.ait.lienzo.client.core.shape.RegularPolygon polygon, final double radius) {
        polygon.setRadius(radius);
        this.moveChild(polygon.getID(), radius, radius);
        return this;
    }


    @Override
    public String toString() {
        return "PolygonShape{}";
    }

    @Override
    public void applyRadius(final double radius, final MutationContext mutationContext) {
        if (radius > 0) {
            _applyPolygonRadius(polygon, radius);
            _applyPolygonRadius(decorator, radius);
        }
    }
}
