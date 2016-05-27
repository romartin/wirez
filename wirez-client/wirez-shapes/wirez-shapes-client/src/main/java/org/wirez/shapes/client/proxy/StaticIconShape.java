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

import org.wirez.core.client.shape.MutationContext;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;
import org.wirez.shapes.client.view.icon.statics.StaticIconShapeView;
import org.wirez.shapes.proxy.icon.statics.IconProxy;
import org.wirez.shapes.proxy.icon.statics.Icons;

public class StaticIconShape<W> extends AbstractProxyShape<W, StaticIconShapeView, IconProxy<W>> {
    
    public StaticIconShape(final StaticIconShapeView view,
                           final IconProxy<W> proxy) {
        super( view, proxy );
    }

    public static <T> Icons getIcon( final IconProxy<T> proxy ) {
        return getIcon( null, proxy );
    }

    public static <T> Icons getIcon( final T definition, final IconProxy<T> proxy ) {

        Icons icon = null;
        
        if ( null != definition ) {
            
            return proxy.getIcon( definition );
            
        }
        
        return icon;
    }
    
    @Override
    public void applyProperties(final Node<View<W>, Edge> element, final MutationContext mutationContext) {
        super.applyProperties(element, mutationContext);
        
        // Icon.
        Icons icon = getIcon( getDefinition( element ), proxy );
        view.setIcon( icon );
        
    }

    @Override
    public String toString() {
        return "StaticIconShape{}";
    }

    
}
