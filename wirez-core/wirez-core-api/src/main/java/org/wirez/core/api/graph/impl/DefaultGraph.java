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


import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.graph.*;

/**
 * Default graph interface that provides an edge store too, so supports unconnected edges (implements HasEdges). 
 * It's useful for graphical modelling as you can just drop your connectors inside the canvas and let them unconnected for some time...
 */
public interface DefaultGraph<C, N extends Node, E extends Edge> 
        extends  Graph<C, N>, Element<C> {

    E addEdge(final E edge);

    E removeEdge(final String uuid);

    E getEdge(final String uuid);

    Iterable<E> edges();
    
    void clear();
    
}
