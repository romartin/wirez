package org.wirez.core.api.graph.processing.visitor;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ParentChildRelationship;
import org.wirez.core.api.graph.content.ViewContent;

public abstract class AbstractContentVisitorCallback 
        extends AbstractVisitorCallback 
        implements ContentVisitorCallback<Node, Edge, Graph<?, Node>> {


    @Override
    public void visitGraphWithViewContent(Graph<? extends ViewContent, ? extends Node> graph) {
        
    }

    @Override
    public void visitNodeWithViewContent(Node<? extends ViewContent, ?> node) {

    }

    @Override
    public void visitEdgeWithViewContent(Edge<? extends ViewContent, ?> edge) {

    }

    @Override
    public void visitEdgeWithParentChildRelationContent(Edge<ParentChildRelationship, ?> edge) {

    }
    
}
