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
import org.wirez.shapes.client.AbstractBasicNamedShape;
import org.wirez.shapes.proxy.BasicNamedShapeProxy;

public abstract class AbstractProxyTitledShape<W, V extends org.wirez.shapes.client.view.BasicShapeView, P extends BasicNamedShapeProxy<W>> 
        extends AbstractBasicNamedShape<W, V> {

    protected final transient P proxy;

    public AbstractProxyTitledShape(final V view,
                              final P proxy ) {
        super(view);
        this.proxy = proxy;
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
    @Override
    protected String getFontFamily(final Node<View<W>, Edge> element) {
        return proxy.getFontFamily( getDefinition( element ) );
    }

    @Override
    protected String getFontColor(final Node<View<W>, Edge> element) {
        return proxy.getFontColor( getDefinition( element ) );
    }

    @Override
    protected Double getFontSize(final Node<View<W>, Edge> element) {
        return proxy.getFontSize( getDefinition( element ) );
    }

    @Override
    protected Double getFontBorderSize(final Node<View<W>, Edge> element) {
        return proxy.getFontBorderSize( getDefinition( element ) );
    }

}
