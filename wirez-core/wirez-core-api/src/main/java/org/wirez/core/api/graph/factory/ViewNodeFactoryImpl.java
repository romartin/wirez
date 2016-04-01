/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * DefinitionITHOUT DefinitionARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wirez.core.api.graph.factory;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.graph.content.view.ViewImpl;
import org.wirez.core.api.graph.impl.NodeImpl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.Set;

@ApplicationScoped
@Named( ViewNodeFactoryImpl.FACTORY_NAME )
public class ViewNodeFactoryImpl extends BaseViewElementFactory<Object, View<Object>, Node<View<Object>, Edge>> implements ViewNodeFactory<Object> {

    public static final String FACTORY_NAME = "viewNodeFactoryImpl";
    
    protected ViewNodeFactoryImpl() {
    }

    @Override
    public Node<View<Object>, Edge> build(final String uuid, final Object definition, final Set<?> properties, final Set<String> labels) {
        Node<View<Object>, Edge> node =
                new NodeImpl<View<Object>>(uuid,
                        (Set<Object>) properties,
                        labels,
                        new ViewImpl<>( definition, buildBounds())

                );

        return node;
    }
    
}
