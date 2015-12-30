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

package org.wirez.core.client.impl;

import com.ait.lienzo.client.core.shape.AbstractDirectionalMultiPointShape;
import com.ait.lienzo.client.core.shape.Decorator;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.OrthogonalPolyLine;
import com.ait.lienzo.client.core.shape.wires.MagnetManager;
import com.ait.lienzo.client.core.shape.wires.WiresConnector;
import com.ait.lienzo.client.core.shape.wires.WiresMagnet;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.client.core.types.Point2DArray;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.client.Shape;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.impl.BaseCanvas;
import org.wirez.core.client.mutation.*;

public abstract class BaseConnector<W extends Definition> extends WiresConnector implements
        Shape<W>,
        HasPositionMutation,
        HasSizeMutation,
        HasPropertyMutation,
        HasGraphElementMutation<W, Edge<W, Node>> {

    protected String id;
    protected Group group;

    public BaseConnector(AbstractDirectionalMultiPointShape<?> line, Decorator<?> head, Decorator<?> tail, WiresManager manager) {
        super(line, head, tail, manager);
        init();
    }

    public BaseConnector(WiresMagnet headMagnet, WiresMagnet tailMagnet, AbstractDirectionalMultiPointShape<?> line, Decorator<?> head, Decorator<?> tail, WiresManager manager) {
        super(headMagnet, tailMagnet, line, head, tail, manager);
        init();
    }

    protected void init() {
        group = new Group();
    }

    public abstract com.ait.lienzo.client.core.shape.Shape getShape();

    @Override
    public Shape<W> setId(final String id) {
        this.id = id;
        return this;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public com.ait.lienzo.client.core.shape.Node getShapeNode() {
        return getDecoratableLine();
    }

    @Override
    public Shape<W> setState(final Shape.ShapeState state) {
        if (Shape.ShapeState.SELECTED.equals(state)) {
            doSelect();
        } else if (Shape.ShapeState.DESELECTED.equals(state)) {
            doDeSelect();
        }
        return this;
    }

    protected void doSelect() {
        // TODO: new WirezSelectionAnimation(this).setDuration(0).run();
    }

    protected void doDeSelect() {
        // TODO: new WirezDeSelectionAnimation(this, 1, 1, ColorName.BLACK).setDuration(0).run();
    }

    @Override
    public boolean accepts(MutationType type) {
        return MutationType.STATIC.equals(type);
    }

    @Override
    public void applyElementPosition(Edge<W, Node> element, CanvasHandler canvasHandler, MutationContext mutationContext) {
        // TODO
    }

    @Override
    public void applyElementSize(Edge<W, Node> element, CanvasHandler canvasHandler, MutationContext mutationContext) {
        // TODO
    }

    @Override
    public void applyElementProperties(Edge<W, Node> element, CanvasHandler canvasHandler, MutationContext mutationContext) {
        
        // TODO

        // Magnet connections.
        _applyConnections(element, canvasHandler);
        
    }

    @Override
    public void applyPosition(double x, double y, MutationContext mutationContext) {
        // TODO
    }

    @Override
    public void applyPropertyValue(String propertyId, Object value, MutationContext mutationContext) {
        // TODO
    }

    @Override
    public void applySize(double width, double height, MutationContext mutationContext) {
        // TODO
    }

    protected Shape<W> _applyConnections(final Edge<W, Node> element, final CanvasHandler canvasHandler) {
        final BaseCanvas canvas = (BaseCanvas) canvasHandler.getSettings().getCanvas();
        final Node sourceNode = element.getSourceNode();
        final Node targetNode = element.getTargetNode();
        if (targetNode != null) {
            final BaseShape outNodeShape = (BaseShape) canvas.getShape(targetNode.getUUID());
            if ( null != sourceNode && null != outNodeShape ) {
                final BaseShape inNodeShape = (BaseShape) canvas.getShape(sourceNode.getUUID());
                // TODO: Magnets concrete control points to use
                connect(inNodeShape.getMagnets(), 3, outNodeShape.getMagnets(), 7, true, false);
            }
        }
        return this;
    }

    protected void connect(MagnetManager.Magnets headMagnets, int headMagnetsIndex, MagnetManager.Magnets tailMagnets, int tailMagnetsIndex,
                           final boolean tailArrow, final boolean headArrow)
    {
        // Obtain the magnets.
        WiresMagnet m0_1 = headMagnets.getMagnet(headMagnetsIndex);
        WiresMagnet m1_1 = tailMagnets.getMagnet(tailMagnetsIndex);

        // Update the magnets.
        this.setHeadMagnet(m0_1);
        this.setTailMagnet(m1_1);

        double x0 = m0_1.getControl().getX();
        double y0 = m0_1.getControl().getY();
        double x1 = m1_1.getControl().getX();
        double y1 = m1_1.getControl().getY();

        // TODO: Update the connector decorator in order to modify head & tail decorators (connector direction)
       /* OrthogonalPolyLine line = createLine(x0, y0, (x0 + ((x1 - x0) / 2)), (y0 + ((y1 - y0) / 2)), x1, y1);
        this.setDecorator(
                line, 
                headArrow ? new SimpleArrow(20, 0.75) : null,
                tailArrow ? new SimpleArrow(20, 0.75) : null);*/

    }

    protected final OrthogonalPolyLine createLine(final double... points)
    {
        return new OrthogonalPolyLine(Point2DArray.fromArrayOfDouble(points)).setCornerRadius(5).setDraggable(true);
    }
}
