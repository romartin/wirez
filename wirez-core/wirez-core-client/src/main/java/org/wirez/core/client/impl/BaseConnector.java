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

import com.google.gwt.logging.client.LogConfiguration;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.graph.content.view.ViewConnector;
import org.wirez.core.client.Shape;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.wires.WiresCanvas;
import org.wirez.core.client.control.BaseDragControl;
import org.wirez.core.client.control.toolbox.BaseToolboxControl;
import org.wirez.core.client.mutation.*;
import org.wirez.core.client.view.IsConnector;
import org.wirez.core.client.view.ShapeView;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BaseConnector<W> implements
        Shape<W>,
        HasPropertyMutation,
        HasGraphElementMutation<W, View<W>, Edge<View<W>, Node>> {

    private static Logger LOGGER = Logger.getLogger("org.wirez.core.client.impl.BaseConnector");
    
    protected String id;
    protected ShapeView view;
    protected BaseDragControl<Shape<W>, Edge> dragControl;
    protected BaseToolboxControl<Shape<W>, Edge> toolboxControl;

    public BaseConnector(final ShapeView view) {
        this.view = view;
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
    public ShapeView getShapeView() {
        return view;
    }

    @Override
    public boolean accepts(MutationType type) {
        return MutationType.STATIC.equals(type);
    }

    protected void _applyFillColor(final String color) {
        if (color != null && color.trim().length() > 0) {
            getShapeView().setFillColor(color);
        }
    }

    protected void  _applyBorders(final String color, final Double width) {
        if (color != null && color.trim().length() > 0) {
            getShapeView().setStrokeColor(color);
        }
        if (width != null) {
            getShapeView().setStrokeWidth(width);
        }
    }

    
    @Override
    public void applyElementPosition(Edge<View<W>, Node> element, CanvasHandler canvasHandler, GraphContext mutationContext) {
        // TODO
    }

    @Override
    public void applyElementProperties(Edge<View<W>, Node> element, CanvasHandler canvasHandler, GraphContext mutationContext) {
        
        // TODO

    }

    @Override
    public void applyPropertyValue(String propertyId, Object value, Context mutationContext) {
        // TODO
    }

    @Override
    public void beforeMutations(final Canvas canvas) {
        
    }

    @Override
    public void afterMutations(final Canvas canvas) {

    }

    public Shape<W> applyConnections(final Edge<View<W>, Node> element, final CanvasHandler canvasHandler) {
        final WiresCanvas canvas = (WiresCanvas) canvasHandler.getCanvas();
        final Node sourceNode = element.getSourceNode();
        final Node targetNode = element.getTargetNode();
        
        final ViewConnector connectionContent = (ViewConnector) element.getContent();
        final int sourceMagnet = connectionContent.getSourceMagnetIndex();
        final int targetMagnet = connectionContent.getTargetMagnetIndex();
        
        if (targetNode != null) {
            final Shape outNodeShape = canvas.getShape(targetNode.getUUID());
            if ( null != sourceNode && null != outNodeShape ) {
                final Shape inNodeShape = canvas.getShape(sourceNode.getUUID());

                ( (IsConnector) view).connect(inNodeShape.getShapeView(), sourceMagnet, outNodeShape.getShapeView(), targetMagnet, true, false);
            }
        }
        
        return this;
    }

    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }
    
}
