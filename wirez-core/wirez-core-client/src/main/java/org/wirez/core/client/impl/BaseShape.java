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
import com.ait.lienzo.client.core.shape.Node;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.wires.LayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.shared.core.types.ColorName;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.property.defaults.Name;
import org.wirez.core.api.graph.*;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.util.ElementUtils;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.control.*;
import org.wirez.core.client.control.resize.BaseResizeControl;
import org.wirez.core.client.control.toolbox.BaseToolboxControl;
import org.wirez.core.client.mutation.*;
import org.wirez.core.client.view.HasFillGradient;
import org.wirez.core.client.view.HasTitle;
import org.wirez.core.client.view.ShapeView;

/**
 * A default Lienzo Wires based shape.
 */
public abstract class BaseShape<W extends Definition> implements 
        org.wirez.core.client.Shape<W>,
        HasPositionMutation,
        HasPropertyMutation,
        HasGraphElementMutation<W, View<W>, org.wirez.core.api.graph.Node<View<W>, Edge>> {

    protected String id;
    protected ShapeView<? extends ShapeView> view;
    protected BaseDragControl<org.wirez.core.client.Shape<W>, org.wirez.core.api.graph.Node> dragControl;
    protected BaseResizeControl<org.wirez.core.client.Shape<W>, org.wirez.core.api.graph.Node> resizeControl;
    protected BaseToolboxControl<org.wirez.core.client.Shape<W>, org.wirez.core.api.graph.Node> toolboxControl;

    public BaseShape(final ShapeView view) {
        this.view = view;
    }

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
    public ShapeView getShapeView() {
        return view;
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
    public void applyElementPosition(final org.wirez.core.api.graph.Node<View<W>, Edge> element,
                                     final CanvasHandler wirezCanvas,
                                     final MutationContext mutationContext) {
        final Double[] position = ElementUtils.getPosition(element.getContent());
        final double x = position[0];
        final double y = position[1];
        applyPosition(x, y, mutationContext);
    }

    @Override
    public void applyElementProperties(final org.wirez.core.api.graph.Node<View<W>, Edge> element,
                                       final CanvasHandler wirezCanvas,
                                       final MutationContext mutationContext) {
        
        // The graph element's name.
        _applyElementName(element, mutationContext);
        
    }

    @Override
    public void applyPosition(final double x, final double y, final MutationContext mutationContext) {
        view.setShapeX(x).setShapeY(y);
    }

    @Override
    public void applyPropertyValue(final String propertyId, 
                                   final Object value, 
                                   final MutationContext mutationContext) {

        if ( Name.ID.equals(propertyId) && view instanceof HasTitle ) {
            final HasTitle hasTitle = (HasTitle) view;
            final String name = (String) value;
            if ( name != null ) {
                hasTitle.setTitle(name);
                hasTitle.refreshTitle();
            } else {
                hasTitle.setTitle(null);
            }
        }
        
    }

    protected void _applyFillColor(final String color) {
        if (color != null && color.trim().length() > 0) {
            ( (HasFillGradient) view).setFillGradient(HasFillGradient.Type.LINEAR, color, "#FFFFFF");
        }
    }

    protected void  _applyBorders(final String color, final Double width) {
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
                               final Double borderSize) {
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

    @Override
    public void beforeMutations(final Canvas canvas) {
        
    }

    @Override
    public void afterMutations(final Canvas canvas) {

        if ( view instanceof HasTitle ) {
            ( ( HasTitle) view ).moveTitleToTop();
        }
    }

    protected void _applyElementName(final org.wirez.core.api.graph.Node<View<W>, Edge> element,
                                     final MutationContext mutationContext) {
        final Name nameProperty = (Name) ElementUtils.getProperty(element, Name.ID);
        String name = nameProperty.getValue();
        applyPropertyValue(Name.ID, name, mutationContext);
    }

    @Override
    public void destroy() {
        // TODO
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getId() == null) return false;

        org.wirez.core.client.Shape d = (org.wirez.core.client.Shape) obj;
        return getId().equals(d.getId());
    }
}
