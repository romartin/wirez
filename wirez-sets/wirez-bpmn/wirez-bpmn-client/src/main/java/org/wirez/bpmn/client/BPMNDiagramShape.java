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

import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.types.LinearGradient;
import com.ait.lienzo.shared.core.types.ColorName;
import org.wirez.bpmn.api.BPMNDiagram;
import org.wirez.bpmn.api.property.general.*;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.util.ElementUtils;
import org.wirez.core.client.HasDecorators;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.impl.BaseShape;
import org.wirez.core.client.mutation.HasGraphElementMutation;
import org.wirez.core.client.mutation.MutationContext;
import org.wirez.core.client.mutation.MutationType;
import org.wirez.core.client.util.ShapeUtils;

import java.util.ArrayList;
import java.util.Collection;

public class BPMNDiagramShape extends WiresShape implements 
        org.wirez.core.client.Shape<BPMNDiagram>,
        HasGraphElementMutation<BPMNDiagram, ViewContent<BPMNDiagram>, DefaultGraph<ViewContent<BPMNDiagram>, Node, Edge>>,
        HasDecorators {

    private String id;
    
    public BPMNDiagramShape(final MultiPath path,
                            final WiresManager manager) {
        super(path, new WiresLayoutContainer(), manager);
        applyDefaultStyle();
        setDraggable(true);
        
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public org.wirez.core.client.Shape<BPMNDiagram> setId(final String id) {
        this.id = id;
        return this;
    }

    @Override
    public Collection<Shape> getDecorators() {
        return new ArrayList<Shape>(1) {{
            add( getPath() ) ;
        }};
    }
    
    @Override
    public void applyElementPosition(DefaultGraph<ViewContent<BPMNDiagram>, Node, Edge> element, CanvasHandler canvasHandler, MutationContext mutationContext) {
        final Double[] pos = ElementUtils.getPosition(element.getContent());
        getPath().setX(pos[0]);
        getPath().setY(pos[1]);
    }

    @Override
    public void applyElementProperties(DefaultGraph<ViewContent<BPMNDiagram>, Node, Edge> element, CanvasHandler canvasHandler, MutationContext mutationContext) {
        final Double[] size = ElementUtils.getSize(element.getContent());
        final double x = getPath().getX();
        final double y = getPath().getY();
        getPath().clear().rect(x, y, size[0], size[1]);
        applyDefaultStyle();
    }

    private void applyDefaultStyle() {
        getPath()
                .setFillAlpha(0.1)
                .setFillColor(ColorName.BLACK)
                .setStrokeWidth(1)
                .setStrokeColor(ColorName.BLACK);
    }
    
    @Override
    public boolean accepts(final MutationType type) {
        return true;
    }

    @Override
    public void beforeMutations(final Canvas canvas) {

    }

    @Override
    public void afterMutations(final Canvas canvas) {

    }

    @Override
    public Shape getShape() {
        return getPath();
    }

    @Override
    public com.ait.lienzo.client.core.shape.Node getShapeContainer() {
        return (com.ait.lienzo.client.core.shape.Node) getContainer();
    }

    @Override
    public void destroy() {

    }
}
