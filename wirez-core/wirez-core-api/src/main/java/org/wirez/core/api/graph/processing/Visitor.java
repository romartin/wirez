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

package org.wirez.core.api.graph.processing;

import org.wirez.core.api.graph.*;
import org.wirez.core.api.graph.impl.ChildRelationship;
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.graph.impl.ViewEdge;
import org.wirez.core.api.graph.impl.ViewNode;

public interface Visitor {

    void visitGraph(DefaultGraph graph);

    void visitViewNode(ViewNode node);

    void visitNode(Node node);

    void visitViewEdge(ViewEdge edge);

    void visitEdge(Edge edge);

    void visitChildRelationship(ChildRelationship childRelationship);

    void visitUnconnectedEdge(Edge edge);

    void endVisit();

    public interface BoundsVisitor {
        void visitBounds(HasView element, Bounds.Bound ul, Bounds.Bound lr);
    }

    public interface PropertyVisitor {
        void visitProperty(Element element, String key, Object value);
    }
    
}