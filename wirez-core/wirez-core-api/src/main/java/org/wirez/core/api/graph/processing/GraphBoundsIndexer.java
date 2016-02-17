package org.wirez.core.api.graph.processing;

import org.wirez.core.api.graph.*;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.processing.visitor.AbstractGraphVisitorCallback;
import org.wirez.core.api.graph.processing.visitor.GraphVisitorCallback;
import org.wirez.core.api.graph.processing.visitor.GraphVisitorImpl;
import org.wirez.core.api.graph.processing.visitor.GraphVisitor;

public class GraphBoundsIndexer {

    private final Graph graph;
    private final GraphVisitor<Graph, Node, Edge> graphVisitor;

    public GraphBoundsIndexer(final Graph graph) {
        this.graph = graph;
        this.graphVisitor = new GraphVisitorImpl();
    }
    
    public Node getNodeAt(final double x, final double y) {
        
        final Node[] result = new Node[1];

        graphVisitor.visit(graph, new AbstractGraphVisitorCallback() {
            
            @Override
            public void visitNodeWithViewContent(Node<? extends ViewContent, ?> node) {
                super.visitNodeWithViewContent(node);
                
                if ( isNodeAt(node, x, y) ) {
                    result[0] = node;
                }
            }
            
        }, GraphVisitor.GraphVisitorPolicy.EDGE_LAST);
        
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
