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

import java.util.Map;
import java.util.Set;

@Portable
public class ViewEdgeImpl<W extends Definition> extends ViewElementImpl<W> implements ViewEdge<W, ViewNode> {

    private ViewNode sourceNode;
    private ViewNode targetNode;

    public ViewEdgeImpl(@MapsTo("id") String id,
                        @MapsTo("definition") W definition,
                        @MapsTo("properties") Map<String, Object> properties,
                        @MapsTo("labels") Set<String> labels,
                        @MapsTo("bounds") Bounds bounds) {
        super(id, definition, properties, labels, bounds);
    }
    
    @Override
    public ViewNode getSourceNode() {
        return sourceNode;
    }

    @Override
    public ViewNode getTargetNode() {
        return targetNode;
    }

    public void setSourceNode(ViewNode sourceNode) {
        this.sourceNode = sourceNode;
    }

    public void setTargetNode(ViewNode targetNode) {
        this.targetNode = targetNode;
    }

}
