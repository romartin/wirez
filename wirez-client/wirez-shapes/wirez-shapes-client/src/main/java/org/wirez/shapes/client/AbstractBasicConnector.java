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

package org.wirez.shapes.client;

import org.wirez.core.client.shape.AbstractConnector;
import org.wirez.core.client.shape.MutationContext;
import org.wirez.core.client.shape.view.ShapeView;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.ViewConnector;

public abstract class AbstractBasicConnector<W, V extends ShapeView>
        extends AbstractConnector<W, Edge<ViewConnector<W>, Node>, V> {

    public AbstractBasicConnector( final V view) {
        super( view );
    }

    @Override
    public void applyProperties(final Edge<ViewConnector<W>, Node> element, final MutationContext mutationContext) {
        super.applyProperties( element, mutationContext );

        // Fill color.
        _applyFillColor( element, mutationContext );

        // Fill alpha.
        _applyFillApha( element, mutationContext );

        // Apply border styles.
        _applyBorders( element, mutationContext );

        // Apply border alpha.
        _applyBorderApha( element, mutationContext );


    }

    protected abstract String getBackgroundColor( Edge<ViewConnector<W>, Node> element );
    protected abstract Double getBackgroundAlpha( Edge<ViewConnector<W>, Node> element );
    protected abstract String getBorderColor( Edge<ViewConnector<W>, Node> element );
    protected abstract Double getBorderSize( Edge<ViewConnector<W>, Node> element );
    protected abstract Double getBorderAlpha( Edge<ViewConnector<W>, Node> element );

    protected AbstractBasicConnector<W, V> _applyFillColor( final Edge<ViewConnector<W>, Node> element, final MutationContext mutationContext ) {
        final String color = getBackgroundColor( element );
        super._applyFillColor( color, mutationContext );
        return this;
    }

    protected AbstractBasicConnector<W, V> _applyFillApha( final Edge<ViewConnector<W>, Node> element, final MutationContext mutationContext ) {
        final Double alpha = getBackgroundAlpha( element );
        super._applyFillAlpha( alpha, mutationContext );
        return this;
    }

    protected AbstractBasicConnector<W, V> _applyBorders( final Edge<ViewConnector<W>, Node> element, final MutationContext mutationContext ) {
        final String color = getBorderColor( element );
        final Double width = getBorderSize( element );
        super._applyBorders( color, width, mutationContext );
        return this;
    }

    protected AbstractBasicConnector<W, V> _applyBorderApha( final Edge<ViewConnector<W>, Node> element, final MutationContext mutationContext ) {
        final Double alpha = getBorderAlpha( element );
        super._applyBorderAlpha( alpha, mutationContext );
        return this;
    }
    

}
