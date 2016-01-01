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
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.impl.*;

public abstract class AbstractGraphVisitorCallback implements DefaultGraphVisitorCallback {
    
    @Override
    public void visitViewNode(ViewNode node) {
        
    }

    @Override
    public void visitDefaultNode(DefaultNode node) {

    }

    @Override
    public void visitViewEdge(ViewEdge edge) {

    }

    @Override
    public void visitDefaultEdge(DefaultEdge edge) {

    }

    @Override
    public void visitChildRelationEdge(ChildRelationEdge edge) {

    }

    @Override
    public void visitGraph(DefaultGraph graph) {

    }

    @Override
    public void visitNode(Node node) {

    }

    @Override
    public void visitEdge(Edge edge) {

    }

    @Override
    public void endVisit() {

    }
    
}
