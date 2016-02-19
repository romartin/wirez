package org.wirez.core.api.graph.processing.util;

import org.wirez.core.api.graph.*;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.processing.visitor.AbstractChildrenVisitorCallback;
import org.wirez.core.api.graph.processing.visitor.VisitorPolicy;
import org.wirez.core.api.graph.processing.visitor.tree.TreeWalkChildrenVisitor;
import org.wirez.core.api.util.ElementUtils;

public class GraphBoundsIndexer {

    private final Graph<?, Node> graph;
    private final TreeWalkChildrenVisitor visitor;

    public GraphBoundsIndexer(final Graph<?, Node> graph) {
        this.graph = graph;
        this.visitor = new TreeWalkChildrenVisitor();
    }

    public Node getNodeAt(final double x, final double y) {

        final Node[] result = new Node[1];

        visitor.visit(graph, new AbstractChildrenVisitorCallback() {

            @Override
            public void visitNode(final Node node) {
                super.visitNode(node);
                if (node.getContent() instanceof ViewContent && isNodeAt(node, 0, 0, x, y)) {
                    result[0] = node;
                }
            }

            @Override
            public void visitChildNode(final Node parent, final Node child) {
                super.visitChildNode(parent, child);
                
                final Object content = parent.getContent();
                
                if ( content instanceof ViewContent ) {
                    final ViewContent viewContent = (ViewContent) content;
                    final Double[] parentCoords = ElementUtils.getPosition(viewContent);
                    if (isNodeAt(child, parentCoords[0], parentCoords[1], x, y)) {
                        result[0] = child;
                    }
                }
                
            }

        }, VisitorPolicy.VISIT_EDGE_BEFORE_TARGET_NODE);
            
        return result[0];
        
    }
    
    private boolean isNodeAt(final Node node,
                             final double parentX, 
                             final double parentY,
                             final double mouseX, 
                             final double mouseY) {
        
        final Object c = node.getContent();
        final Bounds bounds = ( (ViewContent) node.getContent()).getBounds();
        final Bounds.Bound ulBound = bounds.getUpperLeft();
        final Bounds.Bound lrBound = bounds.getLowerRight();
        final double ulX = ulBound.getX() + parentX;
        final double ulY = ulBound.getY() + parentY;
        final double lrX = lrBound.getX() + parentX;
        final double lrY = lrBound.getY() + parentY;
        
        if ( mouseX >= ulX && mouseX <= lrX && 
                mouseY >= ulY && mouseY <= lrY ) {
            return true;
        }
        
        return false;
    }
    
}
