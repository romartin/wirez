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

package org.wirez.basicset.api;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.content.ViewContentImpl;
import org.wirez.core.api.graph.factory.EdgeFactory;
import org.wirez.core.api.graph.impl.EdgeImpl;

import java.util.Map;
import java.util.Set;

public abstract class BasicEdgeDefinition<W extends Definition> extends BasicDefinition implements EdgeFactory<ViewContent<W>> {

    public BasicEdgeDefinition(@MapsTo("id") String id) {
        super(id);
    }

    @Override
    public Edge<ViewContent<W>, Node> build(String uuid, Set<String> labels, Map<String, Object> properties) {
        return new EdgeImpl<ViewContent<W>>(uuid, buildElementProperties(properties),
                 buildElementLabels(labels), new ViewContentImpl<>( (W) this, buildBounds()));
    }


}
