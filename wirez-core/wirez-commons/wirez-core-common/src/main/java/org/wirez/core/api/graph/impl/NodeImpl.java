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
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Portable
public class NodeImpl<C> extends ElementImpl<C> implements Node<C, Edge> {

    private List<Edge> inEdges = new ArrayList<Edge>();
    private List<Edge> outEdges = new ArrayList<Edge>();
    
    public NodeImpl(@MapsTo("uuid") String uuid,
                    @MapsTo("labels") Set<String> labels,
                    @MapsTo("content") C content) {
        super(uuid, labels, content);
    }

    @Override
    public List<Edge> getInEdges() {
        return inEdges;
    }

    @Override
    public List<Edge> getOutEdges() {
        return outEdges;
    }

}
