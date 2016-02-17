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

package org.wirez.core.api.graph.impl;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.uberfire.commons.validation.PortablePreconditions;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.store.GraphNodeStore;

import java.util.Set;

@Portable
public class GraphImpl<C> extends ElementImpl<C> implements Graph<C, Node> {
    private final GraphNodeStore<Node> nodeStore;

    public GraphImpl(@MapsTo("uuid") String uuid,
                     @MapsTo("properties") Set<Property> properties,
                     @MapsTo("labels") Set<String> labels,
                     @MapsTo("content") C content,
                     @MapsTo("nodeStore") GraphNodeStore<Node> nodeStore) {
        super(uuid, properties, labels, content);
        this.nodeStore = PortablePreconditions.checkNotNull( "nodeStore",
                nodeStore );
    }

    @Override
    public Node addNode(final Node node) {
        return nodeStore.add(node);
    }

    @Override
    public Node removeNode(final String uuid) {
        return nodeStore.remove(uuid);
    }

    @Override
    public Node getNode(final String uuid) {
        return nodeStore.get(uuid);
    }

    @Override
    public Iterable<Node> nodes() {
        return nodeStore;
    }

    @Override
    public void clear() {
        nodeStore.clear();
    }
    
}
