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

package org.wirez.client.shapes;

import org.wirez.client.shapes.view.AbstractShapeView;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.graph.util.GraphUtils;
import org.wirez.core.client.shape.Lifecycle;
import org.wirez.core.client.shape.MutationContext;
import org.wirez.core.client.shape.NodeShape;
import org.wirez.core.client.shape.view.HasFillGradient;
import org.wirez.core.client.shape.view.HasRadius;
import org.wirez.core.client.shape.view.HasSize;
import org.wirez.core.client.shape.view.HasTitle;
import org.wirez.core.client.shape.view.animation.AnimationProperties;
import org.wirez.core.client.shape.view.animation.AnimationProperty;
import org.wirez.core.client.shape.view.animation.HasAnimations;
import org.wirez.core.client.util.ShapeUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * A base shape impl for handling contents of node graph elements.
 */
public abstract class AbstractShape<W, E extends Node<View<W>, Edge>, V extends AbstractShapeView> 
        implements 
        NodeShape<W, View<W>, E, V>, 
        Lifecycle,
        HasChildren<AbstractShape<W, Node<View<W>, Edge>, ?>> {

    private static Logger LOGGER = Logger.getLogger(AbstractShape.class.getName());

    protected String uuid;
    protected V view;
    protected List<AbstractShape<W, Node<View<W>, Edge>, ?>> children = new LinkedList<AbstractShape<W, Node<View<W>, Edge>, ?>>();

    public AbstractShape(final V view) {
        this.view = view;
    }

    public void setUUID(final String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getUUID() {
        return uuid;
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

        if ( view instanceof HasTitle ) {
            ( ( HasTitle) view ).moveTitleToTop();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addChild( final AbstractShape<W, Node<View<W>, Edge>, ?> child, 
                          final Layout layout ) {
        
        final HasChildren<AbstractShapeView<?>> hasChildren = 
                (HasChildren<AbstractShapeView<?>>) this.getShapeView();
        
        children.add( child );
        hasChildren.addChild( (AbstractShapeView<?>) child.getShapeView(), layout );
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public void removeChild( final AbstractShape<W, Node<View<W>, Edge>, ?> child ) {

        final HasChildren<AbstractShapeView<?>> hasChildren =
                (HasChildren<AbstractShapeView<?>>) this.getShapeView();

        children.remove( child );
        hasChildren.removeChild( (AbstractShapeView<?>) child.getShapeView() );
        
    }

    @Override
    public void applyPosition( final E element, final MutationContext mutationContext ) {

        final Double[] position = GraphUtils.getPosition(element.getContent());
        final double x = position[0];
        final double y = position[1];

        view.setShapeX(x);
        view.setShapeY(y);
        
        // Do not apply animations on x/y attributes, does not makes much sense for now.
        /*if ( isAnimationMutation(mutationContext) ) {
            
            addAnimationProperties( new AnimationProperties.X( x ), new AnimationProperties.Y( y ) );
            
        } else {

            view.setShapeX(x);
            view.setShapeY(y);
            
        }*/
        
    }
    
    protected void addAnimationProperties(final AnimationProperty<?>... property) {
        final HasAnimations<?> hasAnimations = (HasAnimations<?>) view;
        hasAnimations.addAnimationProperties( property );
    }

    @Override
    public void applyProperties( final E element, final MutationContext mutationContext ) {
        
        // The graph element's name.
        _applyElementName( element, mutationContext );
        
        // Apply properties to children shapes.
        for ( final AbstractShape<W, Node<View<W>, Edge>, ?> child : children ) {
            child.applyProperties( element, mutationContext );
        }
        
    }

    @Override
    public void applyProperty(final E element,
                              final String propertyId,
                              final Object value,
                              final MutationContext mutationContext) {

        // TODO

        // Apply property to children shapes.
        for ( final AbstractShape<W, Node<View<W>, Edge>, ?> child : children ) {
            child.applyProperty( element, propertyId, value, mutationContext );
        }
        
    }

    protected void _applyFillColor( final String color, final MutationContext mutationContext ) {
        if (color != null && color.trim().length() > 0) {
            
            final boolean hasGradient = view instanceof HasFillGradient;
            
            // Fill gradient cannot be animated for now in lienzo.
            if ( !hasGradient && isAnimationMutation(mutationContext) ) {

                addAnimationProperties(new AnimationProperties.FILL_COLOR( color ));

            } else if ( !hasGradient ) {

                view.setFillColor( color );

            } else {

                ( (HasFillGradient) view).setFillGradient(HasFillGradient.Type.LINEAR, color, "#FFFFFF");
                
            }
            
        }
    }

    protected void  _applyBorders(final String color, final Double width, final MutationContext mutationContext) {
        if (color != null && color.trim().length() > 0) {
            view.setStrokeColor(color);
        }
        if (width != null) {
            view.setStrokeWidth(width);
        }
    }

    protected void  _applyFont(final String family,
                               final String color,
                               final Double size,
                               final Double borderSize,
                               final MutationContext mutationContext) {
        if ( view instanceof HasTitle ) {
            final HasTitle hasTitle = (HasTitle) view;
            
            if (family != null && family.trim().length() > 0) {
                hasTitle.setTitleFontFamily(family);
            }
            if (color != null && color.trim().length() > 0) {
                hasTitle.setTitleStrokeColor(color);
            }
            if (size != null && size > 0) {
                hasTitle.setTitleFontSize(size);
            }
            if (borderSize != null && borderSize > 0) {
                hasTitle.setTitleStrokeWidth(borderSize);
            }

            // Refresh to update size changes etc.
            hasTitle.refreshTitle();

        }
    }

    protected void applySize(final HasSize hasSize, final double width, final double height, final MutationContext mutationContext) {

        if ( isAnimationMutation(mutationContext) ) {

            addAnimationProperties( new AnimationProperties.WIDTH( width ), new AnimationProperties.HEIGHT( height ) );

        } else {

            hasSize.setSize(width, height);

        }
    }

    protected void applyRadius(final HasRadius hasRadius, final double radius, final MutationContext mutationContext) {
        
        if (radius > 0) {

            if ( isAnimationMutation(mutationContext) ) {

                addAnimationProperties(new AnimationProperties.RADIUS( radius ));

            } else {

                hasRadius.setRadius(radius);

            }
            
        }
        
    }

    protected abstract String getNamePropertyValue(E element);
    
    protected void _applyElementName(final E element, final MutationContext mutationContext) {
        final String name = getNamePropertyValue( element );

        if (view instanceof HasTitle) {
            final HasTitle hasTitle = (HasTitle) view;
            if (name != null) {
                hasTitle.setTitle(name);
                hasTitle.refreshTitle();
            } else {
                hasTitle.setTitle(null);
            }
        }
        
    }

    @Override
    public void destroy() {
        // TODO
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
        if ( !( o instanceof AbstractShape) ) {
            return false;
        }

        AbstractShape that = (AbstractShape) o;

        return uuid.equals(that.uuid);
    }
}
