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

package org.wirez.core.api.graph.processing.handler;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.impl.*;

import java.util.Collection;

/**
 * The specific graph handler interface for processing and querying operations over the default graph implementation provided by Wirez.
 */
public interface DefaultGraphHandler extends GraphHandler<DefaultGraph, Node, Edge> {

    /**
     * Returns the default node with the given uuid.
     */
    DefaultNode getDefaultNode(String uuid);
    
    /**
     * Returns the view node with the given uuid.
     */
    ViewNode getViewNode(String uuid);

    /**
     * Returns the default edge with the given uuid.
     */
    DefaultEdge getDefaultEdge(String uuid);
    
    /**
     * Returns the view edge with the given uuid.
     */
    ViewEdge getViewEdge(String uuid);
    
    /**
     * Returns the children nodes for a given parent one.
     * Implementations can iterates over the parent-child default relationships in several ways to obtain the best performance.
     */
    Collection<Node> getChildren(Node parent);

}
