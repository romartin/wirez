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

import com.ait.lienzo.client.core.shape.*;
import com.ait.lienzo.client.core.shape.wires.MagnetManager;
import com.ait.lienzo.client.core.shape.wires.WiresConnector;
import com.ait.lienzo.client.core.shape.wires.WiresMagnet;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.client.core.types.Point2DArray;
import com.google.gwt.logging.client.LogConfiguration;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ConnectionContent;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.client.Shape;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.impl.WiresCanvas;
import org.wirez.core.client.control.BaseDragControl;
import org.wirez.core.client.control.toolbox.BaseToolboxControl;
import org.wirez.core.client.mutation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BaseConnector<W extends Definition> extends WiresConnector implements
        Shape<W>,
        HasPositionMutation,
        HasSizeMutation,
        HasPropertyMutation,
        HasGraphElementMutation<W, ViewContent<W>, Edge<ViewContent<W>, Node>> {

    private static Logger LOGGER = Logger.getLogger("org.wirez.core.client.impl.BaseConnector");
    
    protected String id;
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
    }

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
    public com.ait.lienzo.client.core.shape.Shape getShape() {
        return getDecoratableLine();
    }

    @Override
    public com.ait.lienzo.client.core.shape.Node getShapeContainer() {
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

    @Override
    public void beforeMutations(final Canvas canvas) {
        
    }

    @Override
    public void afterMutations(final Canvas canvas) {

    }

    // TODO: Move to AddEdge command?
    public Shape<W> applyConnections(final Edge<ViewContent<W>, Node> element, final CanvasHandler canvasHandler) {
        final WiresCanvas canvas = (WiresCanvas) canvasHandler.getCanvas();
        final Node sourceNode = element.getSourceNode();
        final Node targetNode = element.getTargetNode();
        
        final ConnectionContent connectionContent = (ConnectionContent) element.getContent();
        final int sourceMagnet = connectionContent.getSourceMagnetIndex();
        final int targetMagnet = connectionContent.getTargetMagnetIndex();
        
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
            log(Level.SEVERE, "WARN - HeadMagnet index invalid!");
            headMagnetsIndex = 0;
        }

        if (tailMagnetsIndex < 0) {
            log(Level.SEVERE, "WARN - TailMagnet index invalid!");
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
       OrthogonalPolyLine line = createLine(x0, y0, (x0 + ((x1 - x0) / 2)), (y0 + ((y1 - y0) / 2)), x1, y1);
        /*this.setDecorator(
                line, 
                headArrow ? new SimpleArrow(20, 0.75) : null,
                tailArrow ? new SimpleArrow(20, 0.75) : null);*/

    }

    private final OrthogonalPolyLine createLine(final double... points)
    {
        return new OrthogonalPolyLine(Point2DArray.fromArrayOfDouble(points)).setCornerRadius(5).setDraggable(true);
    }

    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }
    
}
