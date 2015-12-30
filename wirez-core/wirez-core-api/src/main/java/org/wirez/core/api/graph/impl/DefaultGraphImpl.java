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

package org.wirez.core.api.graph.impl;


import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.graph.Bounds;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.store.DefaultGraphEdgeStore;
import org.wirez.core.api.graph.store.DefaultGraphNodeStore;

import java.util.Map;
import java.util.Set;

/**
 * Default graph interface supports unconnected edges (implements HasEdges).
 */
@Portable
public class DefaultGraphImpl<W extends Definition> extends DefaultElement<W> 
        implements DefaultGraph<W, DefaultNode, DefaultEdge> {

    private final DefaultGraphNodeStore nodeStore;
    private final DefaultGraphEdgeStore edgeStore;
    
    public DefaultGraphImpl(@MapsTo("uuid") String uuid,
                            @MapsTo("definition") W definition,
                            @MapsTo("properties") Map<String, Object> properties,
                            @MapsTo("labels") Set<String> labels,
                            @MapsTo("bounds") Bounds bounds,
                            @MapsTo("graphNodeStore") DefaultGraphNodeStore nodeStore,
                            @MapsTo("graphEdgeStore") DefaultGraphEdgeStore edgeStore) {
        super(uuid, definition, properties, labels, bounds);
        this.nodeStore = nodeStore;
        this.edgeStore = edgeStore;
    }

    @Override
    public DefaultNode addNode(final DefaultNode node) {
        return nodeStore.add(node);
    }

    @Override
    public DefaultNode removeNode(final String uuid) {
        return nodeStore.remove(uuid);
    }

    @Override
    public DefaultNode getNode(final String uuid) {
        return nodeStore.get(uuid);
    }

    @Override
    public Iterable<DefaultNode> nodes() {
        return nodeStore;
    }

    public DefaultEdge addEdge(final DefaultEdge edge) {
        return edgeStore.add(edge);
    }

    public DefaultEdge removeEdge(final String uuid) {
        return edgeStore.remove(uuid);
    }

    public DefaultEdge getEdge(final String uuid) {
        return edgeStore.get(uuid);
    }

    public Iterable<DefaultEdge> edges() {
        return edgeStore;
    }

    @Override
    public void clear() {
        nodeStore.clear();
        edgeStore.clear();
    }
    
    @Override
    public Element<W> copy() {
        // TODO: return new DefaultGraph<W>(uuid, definition, properties, labels, bounds, nodeStore, edgeStore);
        return null;
    }
    
}
