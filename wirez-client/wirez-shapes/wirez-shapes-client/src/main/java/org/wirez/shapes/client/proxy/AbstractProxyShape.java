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
import org.wirez.shapes.proxy.BasicShapeProxy;

public abstract class AbstractProxyShape<W, V extends org.wirez.shapes.client.view.BasicShapeView, P extends BasicShapeProxy<W>> extends org.wirez.shapes.client.AbstractBasicShape<W, V> {
    
    protected final P proxy;
    
    public AbstractProxyShape(final V view,
                              final P proxy ) {
        super(view);
        this.proxy = proxy;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void applyProperties( final Node<View<W>, Edge> element, 
                                 final MutationContext mutationContext)  {

        super.applyProperties(element, mutationContext);
       
    }

    @Override
    protected String getBackgroundColor(final Node<View<W>, Edge> element) {
        return proxy.getBackgroundColor( getDefinition( element ) );
    }

    @Override
    protected String getBorderColor(final Node<View<W>, Edge> element) {
        return proxy.getBorderColor( getDefinition( element ) );
    }

    @Override
    protected Double getBorderSize(final Node<View<W>, Edge> element) {
        return proxy.getBorderSize( getDefinition( element ) );
    }

    @Override
    protected String getNamePropertyValue(final Node<View<W>, Edge> element) {
        return proxy.getNamePropertyValue( getDefinition( element ) );
    }

}
