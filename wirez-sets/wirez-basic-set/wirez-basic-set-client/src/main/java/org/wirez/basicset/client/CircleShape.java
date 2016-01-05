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
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.mutation.HasRadiusMutation;
import org.wirez.core.client.mutation.MutationContext;

import java.util.ArrayList;
import java.util.Collection;

public class CircleShape extends BaseBasicShape<org.wirez.basicset.api.Circle> implements HasRadiusMutation {

    public CircleShape(MultiPath path, Group group, WiresManager manager) {
        super(path, group, manager);
    }

    @Override
    public Collection<Shape> getDecorators() {
        return new ArrayList<Shape>() {{
            add( getPath() );
        }};
    }

    @Override
    public Shape getShape() {
        return getPath();
    }

    @Override
    public void applyElementSize(Node<ViewContent<org.wirez.basicset.api.Circle>, Edge> element, CanvasHandler wirezCanvas, MutationContext mutationContext) {
        // Radius.
        _applyRadius(element, mutationContext);
    }

    @Override
    public void applyElementProperties(Node<ViewContent<org.wirez.basicset.api.Circle>, Edge> element, CanvasHandler wirezCanvas, MutationContext mutationContext) {
        super.applyElementProperties(element, wirezCanvas, mutationContext);

        // Radius.
        _applyRadius(element, mutationContext);
    }

    @Override
    public void applyRadius(double radius, MutationContext mutationContext) {
        if (radius > 0) {
            getPath().clear().circle(radius).close();
        }
    }

    @Override
    protected BaseBasicShape<org.wirez.basicset.api.Circle> _applyFillGradientColor(Node<ViewContent<org.wirez.basicset.api.Circle>, Edge> element) {
        _applyFillRadialGradientColor(element);
        return this;
    }

    protected CircleShape _applyRadius(final Node<ViewContent<org.wirez.basicset.api.Circle>, Edge> element, MutationContext mutationContext) {
        final Integer radius = (Integer) element.getProperties().get(RadiusBuilder.PROPERTY_ID);
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
