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

import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.shape.*;
import com.ait.lienzo.client.core.shape.Node;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.shared.core.types.ColorName;
import com.google.gwt.core.client.GWT;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.property.defaults.Name;
import org.wirez.core.api.graph.*;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.util.ElementUtils;
import org.wirez.core.client.HasDecorators;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.control.*;
import org.wirez.core.client.control.resize.BaseResizeControl;
import org.wirez.core.client.control.toolbox.BaseToolboxControl;
import org.wirez.core.client.mutation.*;

import java.util.Collection;

/**
 * A default Lienzo Wires based shape.
 */
public abstract class BaseShape<W extends Definition> extends WiresShape implements 
        org.wirez.core.client.Shape<W>,
        HasPositionMutation,
        HasPropertyMutation,
        HasGraphElementMutation<W, ViewContent<W>, org.wirez.core.api.graph.Node<ViewContent<W>, Edge>> {

    protected String id;
    protected Text text;
    protected BaseDragControl<org.wirez.core.client.Shape<W>, org.wirez.core.api.graph.Node> dragControl;
    protected BaseResizeControl<org.wirez.core.client.Shape<W>, org.wirez.core.api.graph.Node> resizeControl;
    protected BaseToolboxControl<org.wirez.core.client.Shape<W>, org.wirez.core.api.graph.Node> toolboxControl;


    public BaseShape(final MultiPath path, final Group group, final WiresManager manager) {
        super(path, group, manager);
        group.add(path);
        getPath().setDraggable(false);
        text = buildText("");
        this.addChild(text, WiresLayoutContainer.Layout.CENTER);
        text.moveToTop();
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
    public Shape getShape() {
        return getPath();
    }

    @Override
    public Node getShapeContainer() {
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
    public void applyElementPosition(final org.wirez.core.api.graph.Node<ViewContent<W>, Edge> element,
                                     final CanvasHandler wirezCanvas,
                                     final MutationContext mutationContext) {
        final Double[] position = ElementUtils.getPosition(element.getContent());
        final double x = position[0];
        final double y = position[1];
        applyPosition(x, y, mutationContext);
    }

    @Override
    public void applyElementProperties(final org.wirez.core.api.graph.Node<ViewContent<W>, Edge> element,
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
    public void applyPropertyValue(final String propertyId, 
                                   final Object value, 
                                   final MutationContext mutationContext) {
        
        if (Name.ID.equals(propertyId)) {
            final String name = (String) value;
            if ( name != null ) {
                text.setText(name);
            } else {
                text.setText("");
            }
            text.moveToTop();
        }
        
    }

    @Override
    public void beforeMutations(final Canvas canvas) {
        
    }

    @Override
    public void afterMutations(final Canvas canvas) {

        // Ensure top primitives in the layer
        getText().moveToTop();
        
    }

    protected Text buildText(String text) {
        return new Text(text).setFontSize(14).setFillColor(ColorName.BLACK).setStrokeWidth(1).moveToTop();
    }

    public Text getText() {
        return text;
    }

    protected void _applyElementName(final org.wirez.core.api.graph.Node<ViewContent<W>, Edge> element,
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

        try {
            BaseShape d = (BaseShape) obj;
            return getId().equals(d.getId());
        } catch (ClassCastException e) {
            return false;
        }
    }
}
