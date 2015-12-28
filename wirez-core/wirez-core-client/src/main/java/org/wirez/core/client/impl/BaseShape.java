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
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.property.defaultset.NameBuilder;
import org.wirez.core.api.graph.Bounds;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.mutation.*;

import java.util.Collection;

/**
 * A default Lienzo Wires based shape.
 */
public abstract class BaseShape<W extends Definition> extends WiresShape implements 
        org.wirez.core.client.Shape<W>,
        HasPositionMutation,
        HasSizeMutation,
        HasPropertyMutation,
        HasGraphElementMutation<W, org.wirez.core.api.graph.Node<W, Edge>> {

    protected String id;
    protected Text text;
    
    public BaseShape(final MultiPath path, final Group group, final WiresManager manager) {
        super(path, group, manager);
    }

    public abstract Collection<Shape> getDecorators();

    public abstract Shape getShape();
    
    @Override
    public BaseShape<W> setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Node getShapeNode() {
        return getGroup();
    }


    @Override
    public org.wirez.core.client.Shape<W> setState(ShapeState state) {
        if (org.wirez.core.client.Shape.ShapeState.SELECTED.equals(state)) {
            doSelect();
        } else if (org.wirez.core.client.Shape.ShapeState.DESELECTED.equals(state)) {
            doDeSelect();
        }
        return this;
    }

    /*
        ****************************************************************************************
        *                       SHAPE MUTATIONS
        ****************************************************************************************
     */

    @Override
    public boolean accepts(final MutationType type) {
        return MutationType.STATIC.equals(type);
    }

    protected void doSelect() {
        // TODO: new WirezSelectionAnimation(this).setDuration(0).run();
    }

    protected void doDeSelect() {
        // TODO: new WirezDeSelectionAnimation(this, 0, 0, ColorName.BLACK).setDuration(0).run();
    }
    
    @Override
    public void applyElementPosition(final org.wirez.core.api.graph.Node<W, Edge> element,
                                     final CanvasHandler wirezCanvas,
                                     final MutationContext mutationContext) {
        final Bounds.Bound ul = element.getBounds().getUpperLeft();
        final Bounds.Bound lr = element.getBounds().getLowerRight();
        final double x = ul.getX();
        final double y = ul.getY();
        applyPosition(x, y, mutationContext);
    }

    @Override
    public void applyElementSize(final org.wirez.core.api.graph.Node<W, Edge> element,
                                 final CanvasHandler wirezCanvas,
                                 final MutationContext mutationContext) {
        final Bounds.Bound ul = element.getBounds().getUpperLeft();
        final Bounds.Bound lr = element.getBounds().getLowerRight();
        final double w = lr.getX() - ul.getX();
        final double h = lr.getY() - ul.getY();
        applySize(w, h, mutationContext);
    }

    @Override
    public void applyElementProperties(final org.wirez.core.api.graph.Node<W, Edge> element,
                                       final CanvasHandler wirezCanvas,
                                       final MutationContext mutationContext) {
        
        // The graph element's name.
        _applyElementName(element, mutationContext);
        
    }

    @Override
    public void applyPosition(final double x, final double y, final MutationContext mutationContext) {
        getGroup().setX(x).setY(y);
    }

    @Override
    public void applySize(final double width, final double height, final MutationContext mutationContext) {
        _applyShapeSize(getShape(), width, height, mutationContext);
        final Collection<Shape> decorators = getDecorators();
        if ( null != decorators ) {
            for (final Shape decorator : getDecorators()) {
                _applyShapeSize(decorator, width, height, mutationContext);
            }
        }
    }

    @Override
    public void applyPropertyValue(final String propertyId, 
                                   final Object value, 
                                   final MutationContext mutationContext) {
        
        if (NameBuilder.PROPERTY_ID.equals(propertyId)) {
            // TODO
        }
        
    }

    protected void _applyElementName(final org.wirez.core.api.graph.Node<W, Edge> element,
                                     final MutationContext mutationContext) {
        String name = (String) element.getProperties().get(NameBuilder.PROPERTY_ID);
        applyPropertyValue(NameBuilder.PROPERTY_ID, name, mutationContext);
    }

    protected void _applyShapeSize(final Shape shape, final double w, final double h, MutationContext mutationContext) {
        shape.getAttributes().setWidth(w);
        shape.getAttributes().setHeight(h);
    }
    
    @Override
    public void destroy() {
        // TODO
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getId() == null) return false;

        try {
            BaseShape d = (BaseShape) obj;
            return getId().equals(d.getId());
        } catch (ClassCastException e) {
            return false;
        }
    }
}
