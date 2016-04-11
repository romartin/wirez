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

import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.graph.content.view.ViewImpl;
import org.wirez.core.api.graph.impl.NodeImpl;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import java.util.Set;

@Dependent
@Named( ViewNodeFactoryImpl.FACTORY_NAME )
public class ViewNodeFactoryImpl extends BaseElementFactory<Definition, View<Definition>, Node<View<Definition>, Edge>> implements ViewNodeFactory<Definition> {

    public static final String FACTORY_NAME = "viewNodeFactoryImpl";
    
    protected ViewNodeFactoryImpl() {
    }

    @Override
    public Node<View<Definition>, Edge> build(final String uuid, final Definition definition, final Set<Property> properties, final Set<String> labels) {
        Node<View<Definition>, Edge> node =
                new NodeImpl<View<Definition>>(uuid,
                        properties,
                        labels,
                        new ViewImpl<>( definition, buildBounds())

                );

        return node;
    }
    
}