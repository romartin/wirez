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

package org.wirez.core.api.graph.store;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.graph.Edge;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Portable
public class DefaultGraphEdgeStore implements GraphEdgeStore<Edge> {

    protected Map<String, Edge> edges = new HashMap<String, Edge>();
    
    @Override
    public Edge add(final Edge edge) {
        return edges.put(edge.getUUID(), edge);
    }

    @Override
    public Edge remove(final String uuid) {
        return edges.remove(uuid);
    }

    @Override
    public Edge get(final String uuid) {
        return edges.get(uuid);
    }

    @Override
    public int size() {
        return edges.size();
    }

    @Override
    public void clear() {
        edges.clear();
    }

    @Override
    public Iterator<Edge> iterator() {
        return edges.values().iterator();
    }
}
