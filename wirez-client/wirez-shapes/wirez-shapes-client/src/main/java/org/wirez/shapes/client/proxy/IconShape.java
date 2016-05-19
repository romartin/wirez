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
import org.wirez.shapes.proxy.icon.*;

public class IconShape<W> extends org.wirez.shapes.client.proxy.AbstractProxyShape<W, org.wirez.shapes.client.view.IconShapeView, IconProxy<W>> {
    
    public IconShape(final org.wirez.shapes.client.view.IconShapeView view,
                     final IconProxy<W> proxy) {
        super( view, proxy );
    }

    public static <T> ICONS getIcon(final IconProxy<T> proxy ) {
        return getIcon( null, proxy );
    }

    public static <T> ICONS getIcon( final T definition, final IconProxy<T> proxy ) {

        ICONS icon = null;
        
        if ( null != definition && proxy instanceof DynamicIconProxy) {

            final DynamicIconProxy<T> dynamicIconProxy = (DynamicIconProxy<T>) proxy;
            icon = dynamicIconProxy.getIcon( definition );

        } else if ( proxy instanceof MinusIconProxy) {

            icon = ICONS.MINUS;

        } else if ( proxy instanceof PlusIconProxy) {

            icon = ICONS.PLUS;

        } else if ( proxy instanceof XORIconProxy ) {

            icon = ICONS.XOR;
        }
        
        return icon;
    }
    
    @Override
    public void applyProperties(final Node<View<W>, Edge> element, final MutationContext mutationContext) {
        super.applyProperties(element, mutationContext);
        
        
        // Width/Height.
        final Double w = proxy.getWidth( getDefinition( element ) );
        final Double h = proxy.getHeight( getDefinition( element ) );
        _applyWidthAndHeight(element, w, h, mutationContext);

        // Icon.
        ICONS icon = getIcon( getDefinition( element ), proxy );
        view.setIcon( icon );
        
    }

    @Override
    public String toString() {
        return "IconShape{}";
    }

    
}
