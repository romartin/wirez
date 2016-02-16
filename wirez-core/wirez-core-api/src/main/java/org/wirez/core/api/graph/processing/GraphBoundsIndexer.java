package org.wirez.core.api.graph.processing;

import org.wirez.core.api.graph.Bounds;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.graph.processing.handler.DefaultGraphHandler;
import org.wirez.core.api.graph.processing.handler.GraphHandler;
import org.wirez.core.api.graph.processing.visitor.AbstractGraphVisitorCallback;
import org.wirez.core.api.graph.processing.visitor.DefaultGraphVisitor;
import org.wirez.core.api.graph.processing.visitor.DefaultGraphVisitorImpl;
import org.wirez.core.api.graph.processing.visitor.GraphVisitor;
import org.wirez.core.api.util.ElementUtils;

import java.util.Iterator;

public class GraphBoundsIndexer {

    private final DefaultGraph graph;
    private final DefaultGraphVisitor defaultGraphVisitor;

    public GraphBoundsIndexer(final DefaultGraph graph) {
        this.graph = graph;
        this.defaultGraphVisitor = new DefaultGraphVisitorImpl();
    }
    
    public Node getNodeAt(final double x, final double y) {
        
        final Node[] result = new Node[1];
        
        defaultGraphVisitor.visit(graph, new AbstractGraphVisitorCallback() {
            
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
