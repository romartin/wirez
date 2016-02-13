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
 * DefaultDefinitionITHOUT DefaultDefinitionARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wirez.core.client.graph.factory;

import org.wirez.core.api.definition.DefaultDefinition;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.content.ViewContentImpl;
import org.wirez.core.api.graph.factory.BaseElementFactory;
import org.wirez.core.api.graph.factory.NodeFactory;
import org.wirez.core.api.graph.factory.ViewNodeFactory;
import org.wirez.core.api.graph.impl.NodeImpl;
import org.wirez.core.api.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import java.util.Set;

@ApplicationScoped
public class ViewNodeFactoryImpl 
        extends BaseElementFactory<DefaultDefinition, ViewContent<DefaultDefinition>, Node<ViewContent<DefaultDefinition>, Edge>> 
        implements ViewNodeFactory<DefaultDefinition> {

    @Override
    public Node<ViewContent<DefaultDefinition>, Edge> build(final String uuid, final DefaultDefinition definition, final Set<Property> properties, final Set<String> labels) {
        Node<ViewContent<DefaultDefinition>, Edge> node =
                new NodeImpl<ViewContent<DefaultDefinition>>(uuid,
                        properties,
                        labels,
                        new ViewContentImpl<>( definition, buildBounds())

                );

        return node;
    }
    
}
