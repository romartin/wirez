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
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wirez.core.backend.graph.factory;

import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.graph.content.view.ViewImpl;
import org.wirez.core.api.graph.factory.BaseElementFactory;
import org.wirez.core.api.graph.factory.ViewNodeFactory;
import org.wirez.core.api.graph.impl.NodeImpl;

import javax.enterprise.context.Dependent;
import java.util.Set;

@Dependent
public class ViewNodeFactoryImpl<W extends Definition> extends BaseElementFactory<W, View<W>, Node<View<W>, Edge>> implements ViewNodeFactory<W> {

    @Override
    public Node<View<W>, Edge> build(final String uuid, final W definition, final Set<Property> properties, final Set<String> labels) {
        Node<View<W>, Edge> node =
                new NodeImpl<View<W>>(uuid,
                        properties,
                        labels,
                        new ViewImpl<>( definition, buildBounds())

                );

        return node;
    }
    
}
