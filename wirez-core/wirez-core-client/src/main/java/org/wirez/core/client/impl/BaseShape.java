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
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.shared.core.types.ColorName;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.property.defaultset.Name;
import org.wirez.core.api.graph.*;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.util.ElementUtils;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.control.*;
import org.wirez.core.client.control.resize.BaseResizeControl;
import org.wirez.core.client.control.toolbox.BaseToolboxControl;
import org.wirez.core.client.control.toolbox.HasToolboxControl;
import org.wirez.core.client.mutation.*;

import java.util.Collection;

/**
 * A default Lienzo Wires based shape.
 */
public abstract class BaseShape<W extends Definition> extends WiresShape implements 
        org.wirez.core.client.Shape<W>,
        HasPositionMutation,
        HasPropertyMutation,
        HasGraphElementMutation<W, ViewContent<W>, org.wirez.core.api.graph.Node<ViewContent<W>, Edge>>,
        HasDragControl<org.wirez.core.client.Shape<W>, org.wirez.core.api.graph.Node>,
        HasResizeControl<org.wirez.core.client.Shape<W>, org.wirez.core.api.graph.Node>,
        HasToolboxControl<org.wirez.core.client.Shape<W>, org.wirez.core.api.graph.Node> {

    protected String id;
    protected Text text;
    protected BaseDragControl<org.wirez.core.client.Shape<W>, org.wirez.core.api.graph.Node> dragControl;
    protected BaseResizeControl<org.wirez.core.client.Shape<W>, org.wirez.core.api.graph.Node> resizeControl;
    protected BaseToolboxControl<org.wirez.core.client.Shape<W>, org.wirez.core.api.graph.Node> toolboxControl;


    public BaseShape(final MultiPath path, final Group group, final WiresManager manager) {
        super(path, group, manager);
        group.add(path);
        getPath().setDraggable(false);
    }

    public abstract Collection<Shape> getDecorators();
    
    protected Shape getShape() {
        return getPath();
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
                if ( text == null ) {
                    text = buildText(name);
                    this.addChild(text, WiresLayoutContainer.Layout.CENTER);
                } else {
                    text.setText(name);
                }
                text.moveToTop();
            } else {
                this.removeChild(text);
            }
        }
        
    }

    @Override
    public void beforeMutations() {
        
    }

    @Override
    public void afterMutations() {

        // Ensure top primitives in the layer
        getPath().moveToTop();
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
    public void setDragControl(final BaseDragControl<org.wirez.core.client.Shape<W>, org.wirez.core.api.graph.Node> dragControl) {
        this.dragControl = dragControl;
    }

    @Override
    public BaseDragControl<org.wirez.core.client.Shape<W>, org.wirez.core.api.graph.Node> getDragControl() {
        return dragControl;
    }

    @Override
    public void setResizeControl(final BaseResizeControl<org.wirez.core.client.Shape<W>, org.wirez.core.api.graph.Node> resizeControl) {
        this.resizeControl = resizeControl;
    }

    @Override
    public BaseResizeControl<org.wirez.core.client.Shape<W>, org.wirez.core.api.graph.Node> getResizeControl() {
        return resizeControl;
    }

    @Override
    public void setToolboxControl(BaseToolboxControl<org.wirez.core.client.Shape<W>, org.wirez.core.api.graph.Node> toolboxControl) {
        this.toolboxControl = toolboxControl;
    }

    @Override
    public BaseToolboxControl<org.wirez.core.client.Shape<W>, org.wirez.core.api.graph.Node> getToolboxControl() {
        return toolboxControl;
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
