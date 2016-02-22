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

import org.wirez.bpmn.api.property.general.*;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.util.ElementUtils;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.impl.BaseShape;
import org.wirez.core.client.mutation.MutationContext;
import org.wirez.core.client.view.HasTitle;
import org.wirez.core.client.view.ShapeView;

public abstract class BPMNBasicShape<W extends Definition> 
        extends BaseShape<W> {

    public BPMNBasicShape(final ShapeView shapeView) {
        super(shapeView);
    }

    @Override
    public void applyElementProperties(Node<View<W>, Edge> element, CanvasHandler wirezCanvas, MutationContext mutationContext) {
        super.applyElementProperties(element, wirezCanvas, mutationContext);

        // Fill color.
        _applyFillColor(element);

        // Apply border styles.
        _applyBorders(element);

        // Apply font styles.
        _applyFont(element);
        
    }

    protected BPMNBasicShape<W> _applyFillColor(Node<View<W>, Edge> element) {
        final BgColor bgColor = (BgColor) ElementUtils.getProperty(element, BgColor.ID);
        final String color = bgColor.getValue();
        super._applyFillColor(color);
        return this;
    }

    protected BPMNBasicShape<W> _applyBorders(Node<View<W>, Edge> element) {
        final BorderColor borderColor  = (BorderColor) ElementUtils.getProperty(element, BorderColor.ID);
        final BorderSize borderSize = (BorderSize) ElementUtils.getProperty(element, BorderSize.ID);
        final String color = borderColor.getValue();
        final Double width = borderSize.getValue();
        super._applyBorders(color, width);
        return this;
    }

    protected BPMNBasicShape<W> _applyFont(Node<View<W>, Edge> element) {
        
        if ( view instanceof HasTitle ) {
            final FontFamily fontFamily = (FontFamily) ElementUtils.getProperty(element, FontFamily.ID);
            final FontColor fontColor  = (FontColor) ElementUtils.getProperty(element, FontColor.ID);
            final FontSize fontSize = (FontSize) ElementUtils.getProperty(element, FontSize.ID);
            final FontBorderSize fontBorderSize = (FontBorderSize) ElementUtils.getProperty(element, FontBorderSize.ID);
            final String family = fontFamily.getValue();
            final String color = fontColor.getValue();
            final Double size = fontSize.getValue();
            final Double borderSize = fontBorderSize.getValue();
            super._applyFont(family, color, size, borderSize);
        }
        

        return this;
    }
    
}
