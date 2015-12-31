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
import org.wirez.core.api.graph.Node;

import java.util.Map;
import java.util.Set;

@Portable
public class ChildRelationshipImpl extends ElementImpl implements ChildRelationship {
    
    private Node parent;
    private Node child;

    public ChildRelationshipImpl(@MapsTo("uuid") String uuid, 
                                 @MapsTo("properties") Map<String, Object> properties, 
                                 @MapsTo("labels") Set<String> labels) {
        super(uuid, properties, labels);
    }

    @Override
    public void setChildNode(final Node node) {
        this.child = node;
    }

    @Override
    public void setParentNode(final Node node) {
        this.parent = node;
    }

    @Override
    public Node getSourceNode() {
        return parent;
    }

    @Override
    public Node getTargetNode() {
        return child;
    }

}
