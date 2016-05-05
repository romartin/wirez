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
import org.wirez.core.client.shape.Lifecycle;
import org.wirez.core.client.shape.MutableShape;
import org.wirez.core.client.shape.MutationContext;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.view.IsConnector;
import org.wirez.core.client.shape.view.ShapeView;
import org.wirez.core.client.shape.view.animation.AnimationProperties;
import org.wirez.core.client.shape.view.animation.AnimationProperty;
import org.wirez.core.client.shape.view.animation.HasAnimations;
import org.wirez.core.client.util.ShapeUtils;

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
    public void applyPosition(final E element, final MutationContext mutationContext) {
        
    }

    @Override
    public void applyProperties(final E element, final MutationContext mutationContext) {

    }

    @Override
    public void applyProperty(final E element, 
                              final String propertyId, 
                              final Object value,
                              final MutationContext mutationContext) {

    }

    @Override
    public void destroy() {
        // TODO
    }

    protected void _applyFillColor(final String color, final MutationContext mutationContext) {
        
        if (color != null && color.trim().length() > 0) {


            if ( isAnimationMutation(mutationContext) ) {

                addAnimationProperties( new AnimationProperties.FILL_COLOR( color ) );

            } else {

                getShapeView().setFillColor(color);

            }
            
        }
        
    }

    protected void  _applyBorders(final String color, 
                                  final Double width,
                                  final MutationContext mutationContext) {
        
        final boolean isAnimation = isAnimationMutation(mutationContext);
        
        if (color != null && color.trim().length() > 0) {
            
            if ( isAnimation ) {

                addAnimationProperties( new AnimationProperties.STROKE_COLOR( color ) );
                
            } else {

                getShapeView().setStrokeColor(color);

            }
        }
        
        if (width != null) {

            if ( isAnimation ) {

                addAnimationProperties( new AnimationProperties.STROKE_WIDTH( width ) );
                
            } else {

                getShapeView().setStrokeWidth(width);

            }
            
        }
        
    }

    public void applyConnections(final E element, 
                                 final CanvasHandler canvasHandler,
                                 final MutationContext mutationContext) {

        final Node sourceNode = element.getSourceNode();
        final Node targetNode = element.getTargetNode();

        applyConnections( element, sourceNode,  targetNode, canvasHandler, mutationContext);

    }

    public void applyConnections(final E element,
                                 final Node sourceNode,
                                 final Node targetNode,
                                 final CanvasHandler canvasHandler,
                                 final MutationContext mutationContext) {
        
        final WiresCanvas canvas = (WiresCanvas) canvasHandler.getCanvas();

        if (targetNode != null) {
            final Shape outNodeShape = canvas.getShape(targetNode.getUUID());
            if ( null != sourceNode && null != outNodeShape ) {
                final Shape inNodeShape = canvas.getShape(sourceNode.getUUID());
                applyConnections( element, inNodeShape.getShapeView(), outNodeShape.getShapeView(), mutationContext );
            }
        }
        
    }

    public void applyConnections(final E element,
                                 final ShapeView inNodeShapeView,
                                 final ShapeView outNodeShapeView,
                                 final MutationContext mutationContext) {

        final ViewConnector connectionContent = (ViewConnector) element.getContent();
        final int sourceMagnet = connectionContent.getSourceMagnetIndex();
        final int targetMagnet = connectionContent.getTargetMagnetIndex();

        if ( null != inNodeShapeView ) {
            final int z = inNodeShapeView.getZIndex();
            view.setZIndex( z );
        }
        
        if ( null != inNodeShapeView && null != outNodeShapeView ) {
            ( (IsConnector) view).connect(inNodeShapeView, sourceMagnet, outNodeShapeView, targetMagnet, true, false);
        }

    }


    protected void addAnimationProperties(final AnimationProperty<?>... property) {
        final HasAnimations<?> hasAnimations = (HasAnimations<?>) view;
        hasAnimations.addAnimationProperties( property );
    }
    
    protected boolean isStaticMutation( final MutationContext mutationContext ) {
        return ShapeUtils.isStaticMutation( mutationContext );
    }

    protected boolean isAnimationMutation( final MutationContext mutationContext ) {
        return ShapeUtils.isAnimationMutation( view, mutationContext);
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
