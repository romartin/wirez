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

package org.wirez.core.client.shape.factory;

import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.definition.shape.ShapeProxy;
import org.wirez.core.client.shape.view.ShapeGlyph;
import org.wirez.core.client.shape.view.ShapeView;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class AbstractProxyShapeFactory<W, V extends ShapeView, S extends Shape<V>, P extends ShapeProxy<W>> 
        extends AbstractBindableShapeFactory<W, S> implements ShapeProxyFactory<W, AbstractCanvasHandler, S, P> {
    
    protected final Map<Class<?>, P> proxies = new HashMap<Class<?>, P>();
    
    public Set<Class<?>> getSupportedModelClasses() {
        return proxies.keySet();
    }

    @Override
    public String getDescription( final String definitionId ) {
        final P proxy = getProxy( definitionId );
        return proxy.getDescription();
    }

    @Override
    protected String getDescription( final Class<?> clazz ) {
        final String id = getDefinitionId( clazz );
        return getDescription( id );
    }

    @Override
    public void addProxy(final Class<?> clazz, 
                         final P proxy) {
        
            proxies.put( clazz, proxy );

    }

    @SuppressWarnings("unchecked")
    public P getProxy( final Class<?> clazz ) {
        return proxies.get( clazz );
    }
    
    @SuppressWarnings("unchecked")
    public P getProxy( final String definitionId ) {

        for ( final Map.Entry<Class<?>, P> entry : proxies.entrySet() ) {

            final String id = BindableAdapterUtils.getDefinitionId( entry.getKey() );
            if ( id.equals( definitionId ) ) {
                return entry.getValue();
            }
        }

        throw new RuntimeException( "This factory should provide a proxy for Definition [" + definitionId + "]" );
    }

    @Override
    public ShapeGlyph glyph( final String definitionId, 
                             final double width, 
                             final double height ) {
        final Class<?> clazz = getDefinitionClass( definitionId );
        return glyph( clazz, width, height );
    }
    
}
