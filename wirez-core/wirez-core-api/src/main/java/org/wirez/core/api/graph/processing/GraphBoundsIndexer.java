package org.wirez.core.api.graph.processing;

import org.wirez.core.api.graph.Bounds;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.util.ElementUtils;

import java.util.Iterator;

public class GraphBoundsIndexer {

    private final DefaultGraph graph;

    public GraphBoundsIndexer(final DefaultGraph graph) {
        this.graph = graph;
    }
    
    public Node getNodeAt(final double x, final double y) {
        final Iterable<Node> nodesIterable = graph.nodes();
        final Iterator<Node> nodesIt = nodesIterable.iterator();
        while (nodesIt.hasNext()) {
            final Node node = nodesIt.next();
            final Object c = node.getContent();
            if ( c instanceof ViewContent ) {
                final ViewContent viewContent = (ViewContent) c;
                final Bounds bounds = viewContent.getBounds();
                final Bounds.Bound ulBound = bounds.getUpperLeft();
                final Bounds.Bound lrBound = bounds.getLowerRight();
                
                if ( x >= ulBound.getX() && x <= lrBound.getX() 
                        && y >= ulBound.getY() && y <= lrBound.getY() ) {
                    return node;
                }
            }
            
        }
        
        return null;
    }
    
}
