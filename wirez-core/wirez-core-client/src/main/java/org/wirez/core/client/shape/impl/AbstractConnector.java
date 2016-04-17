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

package org.wirez.core.client.shape.impl;

import com.google.gwt.logging.client.LogConfiguration;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.ViewConnector;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.wires.WiresCanvas;
import org.wirez.core.client.shape.MutableShape;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.Lifecycle;
import org.wirez.core.client.shape.view.IsConnector;
import org.wirez.core.client.shape.view.ShapeView;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A base shape impl for handling contents of edge (connector) graph elements.
 * @param <W>
 */
public abstract class AbstractConnector<W, E extends Edge<ViewConnector<W>, Node>, V extends ShapeView>
        implements MutableShape<W, ViewConnector<W>, E, V>, Lifecycle {

    private static Logger LOGGER = Logger.getLogger(AbstractConnector.class.getName());

    protected String uuid;
    protected V view;

    public AbstractConnector(final V view) {
        this.view = view;
    }

    @Override
    public String getUUID() {
        return uuid;
    }

    @Override
    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public V getShapeView() {
        return view;
    }
    
    /*
        ****************************************************************************************
        *                       CONTEXTUAL SHAPE DRAWING & UPDATES
        ****************************************************************************************
     */


    @Override
    public void beforeDraw() {

    }

    @Override
    public void afterDraw() {

    }
    
    @Override
    public void applyPosition(final E element) {
        
    }

    @Override
    public void applyProperties(final E element) {

    }

    @Override
    public void applyProperty(final E element, 
                              final String propertyId, 
                              final Object value) {

    }

    @Override
    public void destroy() {
        // TODO
    }

    protected void _applyFillColor(final String color) {
        if (color != null && color.trim().length() > 0) {
            getShapeView().setFillColor(color);
        }
    }

    protected void  _applyBorders(final String color, 
                                  final Double width) {
        if (color != null && color.trim().length() > 0) {
            getShapeView().setStrokeColor(color);
        }
        if (width != null) {
            getShapeView().setStrokeWidth(width);
        }
    }

    public void applyConnections(final E element, 
                                 final CanvasHandler canvasHandler) {
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
        
    }

    @Override
    public boolean equals(final Object o) {
        if ( this == o ) {
            return true;
        }
        if ( !( o instanceof AbstractConnector) ) {
            return false;
        }

        AbstractConnector that = (AbstractConnector) o;

        return uuid.equals(that.uuid);
    }
    
    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }
    
}
