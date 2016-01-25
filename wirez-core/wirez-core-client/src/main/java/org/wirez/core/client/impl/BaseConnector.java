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
import com.ait.lienzo.client.core.shape.wires.MagnetManager;
import com.ait.lienzo.client.core.shape.wires.WiresConnector;
import com.ait.lienzo.client.core.shape.wires.WiresMagnet;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.google.gwt.core.client.GWT;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.client.Shape;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.impl.BaseCanvas;
import org.wirez.core.client.control.BaseDragControl;
import org.wirez.core.client.control.HasDragControl;
import org.wirez.core.client.control.toolbox.BaseToolboxControl;
import org.wirez.core.client.control.toolbox.HasToolboxControl;
import org.wirez.core.client.mutation.*;

public abstract class BaseConnector<W extends Definition> extends WiresConnector implements
        Shape<W>,
        HasPositionMutation,
        HasSizeMutation,
        HasPropertyMutation,
        HasGraphElementMutation<W, ViewContent<W>, Edge<ViewContent<W>, Node>>,
        HasDragControl<Shape<W>, Edge>,
        HasToolboxControl<Shape<W>, Edge> {

    protected String id;
    protected Group group;
    protected BaseDragControl<Shape<W>, Edge> dragControl;
    protected BaseToolboxControl<Shape<W>, Edge> toolboxControl;

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
    public boolean accepts(MutationType type) {
        return MutationType.STATIC.equals(type);
    }

    @Override
    public void applyElementPosition(Edge<ViewContent<W>, Node> element, CanvasHandler canvasHandler, MutationContext mutationContext) {
        // TODO
    }

    @Override
    public void applyElementProperties(Edge<ViewContent<W>, Node> element, CanvasHandler canvasHandler, MutationContext mutationContext) {
        
        // TODO

        // Magnet connections.
        // _applyConnections(element, canvasHandler);
        
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

    protected Shape<W> _applyConnections(final Edge<ViewContent<W>, Node> element, final CanvasHandler canvasHandler) {
        final BaseCanvas canvas = (BaseCanvas) canvasHandler.getSettings().getCanvas();
        final Node sourceNode = element.getSourceNode();
        final Node targetNode = element.getTargetNode();
        
        final int sourceMagnet = 0;
        final int targetMagnet = 0;
        
        if (targetNode != null) {
            final BaseShape outNodeShape = (BaseShape) canvas.getShape(targetNode.getUUID());
            if ( null != sourceNode && null != outNodeShape ) {
                final BaseShape inNodeShape = (BaseShape) canvas.getShape(sourceNode.getUUID());
                connect(inNodeShape.getMagnets(), sourceMagnet, outNodeShape.getMagnets(), targetMagnet, true, false);
            }
        }
        return this;
    }

    protected void connect(MagnetManager.Magnets headMagnets, int headMagnetsIndex, MagnetManager.Magnets tailMagnets, int tailMagnetsIndex,
                           final boolean tailArrow, final boolean headArrow)
    {
        if (headMagnetsIndex < 0) {
            GWT.log("WARN - HeadMagnet index invalid!");
            headMagnetsIndex = 0;
        }

        if (tailMagnetsIndex < 0) {
            GWT.log("WARN - TailMagnet index invalid!");
            tailMagnetsIndex = 0;
        }
        
        
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

    @Override
    public void setDragControl(final BaseDragControl<Shape<W>, Edge> dragControl) {
        this.dragControl = dragControl;
    }

    @Override
    public BaseDragControl<Shape<W>, Edge> getDragControl() {
        return dragControl;
    }

    @Override
    public void setToolboxControl(BaseToolboxControl<Shape<W>, Edge> toolboxControl) {
        this.toolboxControl = toolboxControl;
    }

    @Override
    public BaseToolboxControl<Shape<W>, Edge> getToolboxControl() {
        return toolboxControl;
    }
    
}
