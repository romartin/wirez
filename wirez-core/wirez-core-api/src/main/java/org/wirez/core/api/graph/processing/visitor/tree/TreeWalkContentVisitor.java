package org.wirez.core.api.graph.processing.visitor.tree;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ParentChildRelationship;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.graph.processing.visitor.ContentVisitorCallback;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 * Visits the graph by walking on the different tree nodes and their children in a recursive way. 
 */
@Dependent
public class TreeWalkContentVisitor extends AbstractTreeWalkVisitor<ContentVisitorCallback<Node, Edge, Graph<?, Node>>> {

    @Inject
    public TreeWalkContentVisitor() {
        super();
    }

    @Override
    protected void doVisitGraph() {
        
        final Object content = graph.getContent();
        if (content instanceof View) {
            callback.visitGraphWithViewContent(graph);
        } else {
            super.doVisitGraph();
        }
        
    }

    @Override
    protected void doVisitNode(final Node node) {
        
        final Object contet = node.getContent();

        if (contet instanceof View) {
            callback.visitNodeWithViewContent(node);
        } else {
            super.doVisitNode(node);
        }
        
    }

    @Override
    protected void doVisitEdge(Edge edge) {
        
        final Object content = edge.getContent();
        if (content instanceof View) {
            callback.visitEdgeWithViewContent(edge);
        } else if (content instanceof ParentChildRelationship) {
            callback.visitEdgeWithParentChildRelationContent(edge);
        } else {
            super.doVisitEdge(edge);
        }
        
    }
    
}
