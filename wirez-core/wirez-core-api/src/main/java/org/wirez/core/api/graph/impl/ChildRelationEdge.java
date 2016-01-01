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
import org.wirez.core.api.graph.Node;

import java.util.Map;
import java.util.Set;

/**
 * A relationship for graph nodes that indicates parent-child semantics..
 */
@Portable
public class ChildRelationEdge<T extends Node> extends ElementImpl implements DefaultEdge<T> {

    public static final String RELATION_NAME = "p-c";

    private T parentNode;
    private T childNode;
    
    public ChildRelationEdge(@MapsTo("uuid") String uuid, 
                             @MapsTo("properties") Map<String, Object> properties, 
                             @MapsTo("labels") Set<String> labels) {
        super(uuid, properties, labels);
    }

    public void setChildNode(final T node) {
        this.childNode = node;   
    }

    public void setParentNode(final T node) {
        this.parentNode = node;
    }

    @Override
    public String getRelationName() {
        return RELATION_NAME;
    }

    @Override
    public T getSourceNode() {
        return parentNode;
    }

    @Override
    public T getTargetNode() {
        return childNode;
    }
}
