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

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.graph.util.GraphUtils;
import org.wirez.core.client.shape.MutationContext;
import org.wirez.core.client.shape.view.HasRadius;
import org.wirez.core.client.shape.view.HasSize;
import org.wirez.core.client.shape.view.HasTitle;
import org.wirez.core.client.shape.view.ShapeView;

public abstract class AbstractBasicShape<W, V extends ShapeView>
    extends AbstractShape<W, Node<View<W>, Edge>, V> {

    public AbstractBasicShape(final V shapeView) {
        super(shapeView);
    }

    @Override
    public void applyProperties(Node<View<W>, Edge> element, final MutationContext mutationContext) {
        super.applyProperties(element, mutationContext);

        // Fill color.
        _applyFillColor(element, mutationContext);

        // Apply border styles.
        _applyBorders(element, mutationContext);

        // Apply font styles.
        _applyFont(element, mutationContext);
    }
    
    protected abstract String getBackgroundColor( Node<View<W>, Edge> element );
    protected abstract String getBorderColor( Node<View<W>, Edge> element );
    protected abstract Double getBorderSize( Node<View<W>, Edge> element );

    protected abstract String getFontFamily( Node<View<W>, Edge> element );
    protected abstract String getFontColor( Node<View<W>, Edge> element );
    protected abstract Double getFontSize( Node<View<W>, Edge> element );
    protected abstract Double getFontBorderSize( Node<View<W>, Edge> element );
    
    
    protected AbstractBasicShape<W, V> _applyFillColor(final Node<View<W>, Edge> element, final MutationContext mutationContext) {
        final String color = getBackgroundColor( element );
        super._applyFillColor(color, mutationContext);
        return this;
    }

    protected AbstractBasicShape<W, V> _applyBorders(final Node<View<W>, Edge> element, final MutationContext mutationContext) {
        final String color = getBorderColor( element );
        final Double width = getBorderSize( element );
        super._applyBorders(color, width, mutationContext);
        
        return this;
    }

    protected AbstractBasicShape<W, V> _applyFont(final Node<View<W>, Edge> element, final MutationContext mutationContext) {
        
        if ( view instanceof HasTitle ) {
            final String family = getFontFamily( element );
            final String color = getFontColor( element );
            final Double size = getFontSize( element );
            final Double borderSize = getFontBorderSize( element );
            super._applyFont(family, color, size, borderSize, mutationContext);
        }

        return this;
    }

    protected AbstractBasicShape<W, V> _applyWidthAndHeight(final Node<View<W>, Edge> element, 
                                                            final Double width,
                                                            final Double height,
                                                            final MutationContext mutationContext) {
        applySize((HasSize) getShapeView(), width, height, mutationContext);
        GraphUtils.updateBounds(width, height, element.getContent());
        return this;
    }

    protected AbstractBasicShape<W, V> _applyRadius(final Node<View<W>, Edge> element,
                                                    final Double radius, 
                                                    final MutationContext mutationContext) {
        if ( null != radius ) {
            applyRadius((HasRadius) getShapeView(), radius, mutationContext);
            GraphUtils.updateBounds(radius, element.getContent());
        }
        return this;
    }
    
    protected W getDefinition( final Node<View<W>, Edge> element ) {
        return element.getContent().getDefinition();
    }

}
