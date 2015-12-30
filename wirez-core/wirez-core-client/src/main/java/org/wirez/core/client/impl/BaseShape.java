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
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.shared.core.types.ColorName;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.property.defaultset.NameBuilder;
import org.wirez.core.api.graph.Bounds;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.impl.ViewEdge;
import org.wirez.core.api.graph.impl.ViewNode;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.control.DefaultDragControl;
import org.wirez.core.client.control.DefaultResizeControl;
import org.wirez.core.client.control.HasDragControl;
import org.wirez.core.client.control.HasResizeControl;
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
        HasGraphElementMutation<W, org.wirez.core.api.graph.impl.ViewNode<W, Edge>>,
        HasDragControl<org.wirez.core.client.Shape<W>, ViewNode>,
        HasResizeControl<org.wirez.core.client.Shape<W>, ViewNode> {

    protected String id;
    protected Text text;
    protected DefaultDragControl<org.wirez.core.client.Shape<W>, ViewNode> dragControl;
    protected DefaultResizeControl<org.wirez.core.client.Shape<W>, ViewNode> resizeControl;
    
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


    /*
        ****************************************************************************************
        *                       SHAPE MUTATIONS
        ****************************************************************************************
     */

    @Override
    public boolean accepts(final MutationType type) {
        return MutationType.STATIC.equals(type);
    }

    @Override
    public void applyElementPosition(final ViewNode<W, Edge> element,
                                     final CanvasHandler wirezCanvas,
                                     final MutationContext mutationContext) {
        final Bounds.Bound ul = element.getBounds().getUpperLeft();
        final Bounds.Bound lr = element.getBounds().getLowerRight();
        final double x = ul.getX();
        final double y = ul.getY();
        applyPosition(x, y, mutationContext);
    }

    @Override
    public void applyElementSize(final ViewNode<W, Edge> element,
                                 final CanvasHandler wirezCanvas,
                                 final MutationContext mutationContext) {
        final Bounds.Bound ul = element.getBounds().getUpperLeft();
        final Bounds.Bound lr = element.getBounds().getLowerRight();
        final double w = lr.getX() - ul.getX();
        final double h = lr.getY() - ul.getY();
        applySize(w, h, mutationContext);
    }

    @Override
    public void applyElementProperties(final ViewNode<W, Edge> element,
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
            final String name = (String) value;
            if ( name != null ) {
                if ( text == null ) {
                    text = buildText(name);
                    this.addChild(text, WiresLayoutContainer.Layout.CENTER);
                } else {
                    text.setText(name);
                }
            } else {
                this.removeChild(text);
            }
        }
        
    }

    protected Text buildText(String text) {
        return new Text(text).setFontSize(14).setFillColor(ColorName.BLACK).setStrokeWidth(1).moveToTop();
    }

    protected Text getText() {
        return text;
    }

    protected void _applyElementName(final ViewNode<W, Edge> element,
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
    public void setDragControl(final DefaultDragControl<org.wirez.core.client.Shape<W>, ViewNode> dragControl) {
        this.dragControl = dragControl;
    }

    @Override
    public DefaultDragControl<org.wirez.core.client.Shape<W>, ViewNode> getDragControl() {
        return dragControl;
    }

    @Override
    public void setResizeControl(final DefaultResizeControl<org.wirez.core.client.Shape<W>, ViewNode> resizeControl) {
        this.resizeControl = resizeControl;
    }

    @Override
    public DefaultResizeControl<org.wirez.core.client.Shape<W>, ViewNode> getResizeControl() {
        return resizeControl;
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
