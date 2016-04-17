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

package org.wirez.bpmn.client.shape;

import org.wirez.bpmn.api.property.general.BackgroundSet;
import org.wirez.bpmn.api.property.general.FontSet;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.client.shape.impl.AbstractShape;
import org.wirez.core.client.shape.view.HasTitle;
import org.wirez.core.client.shape.view.ShapeView;

public abstract class BPMNBasicShape<W, V extends ShapeView>
    extends AbstractShape<W, Node<View<W>, Edge>, V> {

    public BPMNBasicShape(final V shapeView) {
        super(shapeView);
    }

    @Override
    public void applyProperties(Node<View<W>, Edge> element) {
        super.applyProperties(element);

        // Fill color.
        _applyFillColor(element);

        // Apply border styles.
        _applyBorders(element);

        // Apply font styles.
        _applyFont(element);
    }

    protected abstract BackgroundSet getBackgroundSet(Node<View<W>, Edge> element);

    protected abstract FontSet getFontSet(Node<View<W>, Edge> element);
    
    protected BPMNBasicShape<W, V> _applyFillColor(final Node<View<W>, Edge> element) {
        final String color = getBackgroundSet( element ).getBgColor().getValue();
        super._applyFillColor(color);
        
        return this;
    }

    protected BPMNBasicShape<W, V> _applyBorders(final Node<View<W>, Edge> element) {
        final String color = getBackgroundSet( element ).getBorderColor().getValue();
        final Double width = getBackgroundSet( element ).getBorderSize().getValue();
        super._applyBorders(color, width);
        
        return this;
    }

    protected BPMNBasicShape<W, V> _applyFont(final Node<View<W>, Edge> element) {
        
        if ( view instanceof HasTitle ) {
            final String family = getFontSet( element ).getFontFamily().getValue();
            final String color = getFontSet( element ).getFontColor().getValue();
            final Double size = getFontSet( element ).getFontSize().getValue();
            final Double borderSize = getFontSet( element ).getFontBorderSize().getValue();
            super._applyFont(family, color, size, borderSize);
        }

        return this;
    }
    
    
    
}
