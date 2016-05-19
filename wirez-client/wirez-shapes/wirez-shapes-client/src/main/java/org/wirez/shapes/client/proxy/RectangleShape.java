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

package org.wirez.shapes.client.proxy;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.client.shape.MutationContext;
import org.wirez.shapes.proxy.RectangleProxy;
import org.wirez.shapes.client.view.RectangleView;

public class RectangleShape<W> extends AbstractProxyTitledShape<W, RectangleView, RectangleProxy<W>> {
    
    
    public RectangleShape( final org.wirez.shapes.client.view.RectangleView view,
                           final RectangleProxy<W> proxy ) {
        super( view, proxy );
    }

    @Override
    public void applyProperties(final Node<View<W>, Edge> element, final MutationContext mutationContext) {
        super.applyProperties(element, mutationContext);
        
        // Width/Height.
        final Double w = proxy.getWidth( getDefinition( element ) );
        final Double h = proxy.getHeight( getDefinition( element ) );
        _applyWidthAndHeight(element, w, h, mutationContext);
        
    }

    @Override
    public String toString() {
        return "RectangleShape{}";
    }
    
}
