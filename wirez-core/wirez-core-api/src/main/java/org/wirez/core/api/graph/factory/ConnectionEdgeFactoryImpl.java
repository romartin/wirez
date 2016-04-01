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
import org.wirez.core.api.graph.content.view.ViewConnectorImpl;
import org.wirez.core.api.graph.impl.EdgeImpl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.Set;

@ApplicationScoped
@Named( ConnectionEdgeFactoryImpl.FACTORY_NAME )
public class ConnectionEdgeFactoryImpl
        extends BaseViewElementFactory<Object, View<Object>, Edge<View<Object>, Node>> implements ConnectionEdgeFactory<Object> {

    public static final String FACTORY_NAME = "connectorEdgeFactoryImpl";
    
    protected ConnectionEdgeFactoryImpl() {
    }

    @Override
    public Edge<View<Object>, Node> build(String uuid, Object definition, Set<?> properties, Set<String> labels) {
        Edge<View<Object>, Node> edge =
                new EdgeImpl<View<Object>>( uuid,
                        (Set<Object>) properties,
                        labels,
                        new ViewConnectorImpl<>( definition, buildBounds()));
        
        return edge;
    }
}
