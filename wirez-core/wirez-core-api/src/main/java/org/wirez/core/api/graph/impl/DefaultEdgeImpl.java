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

import java.util.Map;
import java.util.Set;

@Portable
public class DefaultEdgeImpl<W extends Definition> extends DefaultElement<W> implements DefaultEdge<W, DefaultNode> {

    private DefaultNode sourceNode;
    private DefaultNode targetNode;

    public DefaultEdgeImpl(@MapsTo("id") String id,
                           @MapsTo("definition") W definition,
                           @MapsTo("properties") Map<String, Object> properties,
                           @MapsTo("labels") Set<String> labels,
                           @MapsTo("bounds") Bounds bounds) {
        super(id, definition, properties, labels, bounds);
    }
    
    @Override
    public DefaultNode getSourceNode() {
        return sourceNode;
    }

    @Override
    public DefaultNode getTargetNode() {
        return targetNode;
    }

    public void setSourceNode(DefaultNode sourceNode) {
        this.sourceNode = sourceNode;
    }

    public void setTargetNode(DefaultNode targetNode) {
        this.targetNode = targetNode;
    }

    @Override
    public Element<W> copy() {
        // TODO
        /*final DefaultEdgeImpl edge = new DefaultEdgeImpl<W>(uuid, definition, properties, labels, bounds);
        edge.setTargetNode(targetNode);
        edge.setSourceNode(sourceNode);
        return edge;*/
        return null;
    }
}
