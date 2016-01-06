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

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import org.wirez.basicset.api.property.size.HeightBuilder;
import org.wirez.basicset.api.property.size.WidthBuilder;
import org.wirez.core.api.graph.Bounds;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.mutation.HasSizeMutation;
import org.wirez.core.client.mutation.MutationContext;

import java.util.ArrayList;
import java.util.Collection;

public class RectangleShape extends BaseBasicShape<org.wirez.basicset.api.Rectangle> implements HasSizeMutation {

    public RectangleShape(Group group, WiresManager manager) {
        super(new MultiPath().rect(0, 0, org.wirez.basicset.api.Rectangle.WIDTH, org.wirez.basicset.api.Rectangle.HEIGHT), group, manager);
    }

    @Override
    public Collection<Shape> getDecorators() {
        return new ArrayList<Shape>() {{
            add( getPath() );
        }};
    }

    @Override
    public void applyElementProperties(Node<ViewContent<org.wirez.basicset.api.Rectangle>, Edge> element, CanvasHandler wirezCanvas, MutationContext mutationContext) {
        super.applyElementProperties(element, wirezCanvas, mutationContext);
        
        // Size.
        _applySize(element, mutationContext);
        
    }

    protected RectangleShape _applySize(final Node<ViewContent<org.wirez.basicset.api.Rectangle>, Edge> element, MutationContext mutationContext) {
        final Integer width = (Integer) element.getProperties().get(WidthBuilder.PROPERTY_ID);
        final Integer height = (Integer) element.getProperties().get(HeightBuilder.PROPERTY_ID);
        applySize(width, height, mutationContext);
        return this;
    }

    @Override
    public String toString() {
        return "RectangleShape{}";
    }

    @Override
    public void applySize(final double width, final double height, final MutationContext mutationContext) {
        final double x = getPath().getX();
        final double y = getPath().getY();
        getPath().clear().rect(x, y, width, height);
    }
}
