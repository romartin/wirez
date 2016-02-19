package org.wirez.core.api.graph.processing.visitor;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;

public abstract class AbstractVisitorCallback implements VisitorCallback<Node, Edge, Graph<?, Node>> {
    
    @Override
    public void visitGraph(Graph<?, Node> graph) {
        
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
