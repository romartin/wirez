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

package org.wirez.bpmn.client;

import com.ait.lienzo.client.core.shape.AbstractDirectionalMultiPointShape;
import com.ait.lienzo.client.core.shape.Decorator;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import org.wirez.bpmn.api.property.general.BgColor;
import org.wirez.bpmn.api.property.general.BorderColor;
import org.wirez.bpmn.api.property.general.BorderSize;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.util.PropertyUtils;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.impl.BaseConnector;
import org.wirez.core.client.mutation.MutationContext;

public abstract class BPMNBasicConnector<W extends Definition> extends BaseConnector<W> {


    public BPMNBasicConnector(final AbstractDirectionalMultiPointShape<?> line, 
                              final Decorator<?> head, 
                              final Decorator<?> tail, 
                              final WiresManager manager) {
        super(line, head, tail, manager);
    }

    @Override
    public Shape getShape() {
        return getDecoratableLine();
    }

    @Override
    public void applyElementProperties(Edge<ViewContent<W>, Node> element, CanvasHandler canvasHandler, MutationContext mutationContext) {
        super.applyElementProperties(element, canvasHandler, mutationContext);

        // Fill color.
        _applyFillColor(element);

        // Apply border styles.
        _applyBorders(element);

        // Apply font styles.
        _applyFont(element);
    }

    protected BPMNBasicConnector<W> _applyFillColor(Edge<ViewContent<W>, Node> element) {
        final BgColor bgColor = (BgColor) PropertyUtils.getProperty(element.getProperties(), BgColor.ID);
        final String color = bgColor.getValue();
        if (color != null && color.trim().length() > 0) {
            getShape().setFillColor(color);
        }
        return this;
    }

    protected BPMNBasicConnector<W> _applyBorders(Edge<ViewContent<W>, Node> element) {
        final BorderColor borderColor  = (BorderColor) PropertyUtils.getProperty(element.getProperties(), BorderColor.ID);
        final BorderSize borderSize = (BorderSize) PropertyUtils.getProperty(element.getProperties(), BorderSize.ID);
        final String color = borderColor.getValue();
        final Integer width = borderSize.getValue();
        if (color != null && color.trim().length() > 0) {
            getShape().setStrokeColor(color);
        }
        if (width != null) {
            getShape().setStrokeWidth(width);
        }
        return this;
    }

    protected BPMNBasicConnector<W> _applyFont(Edge<ViewContent<W>, Node> element) {
        // TODO
        /*final Text text = super.getText();
        if ( null != text ) {
            final String color = (String) element.getProperties().get(FontColorBuilder.PROPERTY_ID);
            final Integer size = (Integer) element.getProperties().get(FontSizeBuilder.PROPERTY_ID);
            final Integer borderSize = (Integer) element.getProperties().get(FontBorderSizeBuilder.PROPERTY_ID);
            if (color != null && color.trim().length() > 0) {
                text.setStrokeColor(color);
            }
            if (size != null && size > 0) {
                text.setFontSize(size);
            }
            if (borderSize != null && borderSize > 0) {
                text.setStrokeWidth(borderSize);
            }

        }*/

        return this;
    }
    
}
