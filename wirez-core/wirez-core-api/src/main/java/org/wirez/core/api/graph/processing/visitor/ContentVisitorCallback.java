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

package org.wirez.core.api.graph.processing.visitor;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ParentChildRelationship;
import org.wirez.core.api.graph.content.view.View;

/**
 * A generic graph visitor callback interface based on the element's content.
 */
public interface ContentVisitorCallback<N extends Node, E extends Edge, G extends Graph<?, N>>
        extends VisitorCallback<N, E, G> {

    void visitGraphWithViewContent(Graph<? extends View, ? extends Node> graph);

    void visitNodeWithViewContent(Node<? extends View, ?> node);

    void visitEdgeWithViewContent(Edge<? extends View, ?> edge);

    void visitEdgeWithParentChildRelationContent(Edge<ParentChildRelationship, ?> edge);

}
