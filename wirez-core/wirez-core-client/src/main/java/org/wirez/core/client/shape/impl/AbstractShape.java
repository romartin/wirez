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

import org.wirez.core.api.definition.property.defaults.Name;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.graph.util.GraphUtils;
import org.wirez.core.client.shape.Lifecycle;
import org.wirez.core.client.shape.MutableShape;
import org.wirez.core.client.shape.MutationContext;
import org.wirez.core.client.shape.view.*;
import org.wirez.core.client.shape.view.animation.AnimationProperties;
import org.wirez.core.client.shape.view.animation.AnimationProperty;
import org.wirez.core.client.shape.view.animation.HasAnimations;
import org.wirez.core.client.util.ShapeUtils;

import java.util.logging.Logger;

/**
 * A base shape impl for handling contents of node graph elements.
 */
public abstract class AbstractShape<W, E extends Node<View<W>, Edge>, V extends ShapeView> 
        implements MutableShape<W, View<W>, E, V>, Lifecycle {

    private static Logger LOGGER = Logger.getLogger(AbstractShape.class.getName());

    protected String uuid;
    protected V view;

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
        
    }

    @Override
    public void applyProperty(final E element,
                              final String propertyId,
                              final Object value,
                              final MutationContext mutationContext) {

        if (Name.ID.equals(propertyId) && view instanceof HasTitle) {
            final HasTitle hasTitle = (HasTitle) view;
            final String name = (String) value;
            if (name != null) {
                hasTitle.setTitle(name);
                hasTitle.refreshTitle();
            } else {
                hasTitle.setTitle(null);
            }
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
                hasTitle.setFontFamily(family);
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

    protected abstract Name getNameProperty(E element);
    
    protected void _applyElementName(final E element, final MutationContext mutationContext) {
        final Name nameProperty = getNameProperty( element );
        String name = nameProperty.getValue();
        applyProperty(element, Name.ID, name, mutationContext);
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
