package org.wirez.core.api.graph.processing.util;

import org.wirez.core.api.graph.*;
import org.wirez.core.api.graph.content.view.Bounds;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.graph.processing.traverse.content.AllEdgesTraverseCallback;
import org.wirez.core.api.graph.processing.traverse.content.AllEdgesTraverseProcessor;
import org.wirez.core.api.graph.processing.traverse.content.AllEdgesTraverseProcessorImpl;
import org.wirez.core.api.graph.processing.traverse.tree.TreeWalkTraverseProcessorImpl;
import org.wirez.core.api.util.ElementUtils;

public class GraphBoundsIndexer {

    private final Graph<View, Node<View, Edge>> graph;
    private final AllEdgesTraverseProcessor visitor;

    public GraphBoundsIndexer(final Graph<View, Node<View, Edge>> graph) {
        this.graph = graph;
        this.visitor = new AllEdgesTraverseProcessorImpl(new TreeWalkTraverseProcessorImpl());
    }

    public Node getNodeAt(final double x, final double y) {

        final Node[] result = new Node[1];

        visitor.traverse(graph, new AllEdgesTraverseCallback<Node<View, Edge>, Edge<Object, Node>>() {
            
                    double parentX = 0;
                    double parentY = 0;
            
                    @Override
                    public void traverseViewEdge(final Edge<Object, Node> edge) {
                        
                    }

                    @Override
                    public void traverseChildEdge(final Edge<Object, Node> edge) {

                        final Node parent = edge.getSourceNode();
                        final Object content = parent.getContent();

                        if (content instanceof View) {
                            final View viewContent = (View) content;
                            final Double[] parentCoords = ElementUtils.getPosition(viewContent);
                            this.parentX = parentCoords[0];
                            this.parentY = parentCoords[1];
                        }
                        
                    }

                    @Override
                    public void traverseParentEdge(final Edge<Object, Node> edge) {

                    }

                    @Override
                    public void traverse(final Edge<Object, Node> edge) {

                    }

                    @Override
                    public void traverseView(final Graph<View, Node<View, Edge>> graph) {

                    }

                    @Override
                    public void traverseView(final Node<View, Edge> node) {
                        if (isNodeAt(node, parentX, parentY, x, y)) {
                            result[0] = node;
                        }
                        this.parentX = 0;
                        this.parentY = 0;
                    }

                    @Override
                    public void traverseCompleted() {

                    }
            
                });
               
        return result[0];
        
    }
    
    private boolean isNodeAt(final Node node,
                             final double parentX, 
                             final double parentY,
                             final double mouseX, 
                             final double mouseY) {
        
        final Object c = node.getContent();
        final Bounds bounds = ( (View) node.getContent()).getBounds();
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
