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

package org.wirez.core.api.graph;

/**
 * <p>A graph edge/relationship. It provides directed relationships between two nodes in order to provider
 * multi-directional graphs.</p>
 */
public interface Edge<C, N extends Node> extends Element<C> {

    N getSourceNode();

    void setSourceNode(N node);
    
    N getTargetNode();

    void setTargetNode(N node);

}