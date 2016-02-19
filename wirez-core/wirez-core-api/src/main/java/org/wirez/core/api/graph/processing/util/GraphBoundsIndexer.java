package org.wirez.core.api.graph.processing.util;

import org.wirez.core.api.graph.*;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.processing.visitor.AbstractContentVisitorCallback;
import org.wirez.core.api.graph.processing.visitor.VisitorPolicy;
import org.wirez.core.api.graph.processing.visitor.tree.TreeWalkContentVisitor;

public class GraphBoundsIndexer {

    private final Graph<?, Node> graph;
    private final TreeWalkContentVisitor visitor;

    public GraphBoundsIndexer(final Graph<?, Node> graph) {
        this.graph = graph;
        this.visitor = new TreeWalkContentVisitor();
    }
    
    public Node getNodeAt(final double x, final double y) {
        
        final Node[] result = new Node[1];

        visitor.visit(graph, new AbstractContentVisitorCallback() {

            @Override
            public void visitNodeWithViewContent(Node<? extends ViewContent, ?> node) {
                super.visitNodeWithViewContent(node);
                if (isNodeAt(node, x, y)) {
                    result[0] = node;
                }
            }

        }, VisitorPolicy.VISIT_EDGE_AFTER_TARGET_NODE);
            
        return result[0];
        
    }
    
    private boolean isNodeAt(final Node node, final double x, final double y) {
        final Object c = node.getContent();
        if ( c instanceof ViewContent ) {
            final ViewContent viewContent = (ViewContent) c;
            final Bounds bounds = viewContent.getBounds();
            final Bounds.Bound ulBound = bounds.getUpperLeft();
            final Bounds.Bound lrBound = bounds.getLowerRight();

            if ( x >= ulBound.getX() && x <= lrBound.getX()
                    && y >= ulBound.getY() && y <= lrBound.getY() ) {
                return true;
            }
        }
        return false;
    }
    
}
