package org.wirez.core.graph.processing.index.bounds;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Graph;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.relationship.Child;
import org.wirez.core.graph.content.view.Bounds;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.graph.processing.traverse.content.AbstractChildrenTraverseCallback;
import org.wirez.core.graph.processing.traverse.content.AbstractContentTraverseCallback;
import org.wirez.core.graph.processing.traverse.content.ChildrenTraverseProcessorImpl;
import org.wirez.core.graph.processing.traverse.tree.TreeWalkTraverseProcessorImpl;
import org.wirez.core.graph.util.GraphUtils;

import javax.enterprise.context.Dependent;
import java.util.Iterator;
import java.util.Stack;

@Dependent
public class GraphBoundsIndexerImpl implements GraphBoundsIndexer {

    private Graph<View, Node<View, Edge>> graph;
    private String rootUUID = null;

    @Override
    public GraphBoundsIndexerImpl build( Graph<View, Node<View, Edge>> graph ) {
        this.graph = graph;
        return this;
    }

    @Override
    public Node<View<?>, Edge> getAt( final double x,
                                      final double y ) {
        return traverseChildren( x, y );
    }

    @Override
    public void destroy() {
        this.graph = null;
    }

    public Node<View<?>, Edge> traverseChildren( final double x, final double y ) {
        final Node[] result = new Node[1];


        new ChildrenTraverseProcessorImpl( new TreeWalkTraverseProcessorImpl() ).traverse( graph, new AbstractChildrenTraverseCallback<Node<View, Edge>, Edge<Child, Node>>() {

            @Override
            public void startNodeTraversal( final Node<View, Edge> node ) {
                super.startNodeTraversal( node );

                onStartNodeTraversal( null, node );
            }

            @Override
            public boolean startNodeTraversal( final Iterator<Node<View, Edge>> parents,
                                               final Node<View, Edge> node ) {
                super.startNodeTraversal( parents, node );

                onStartNodeTraversal( parents, node );

                return true;
            }

            private void onStartNodeTraversal( final Iterator<Node<View, Edge>> parents,
                                                final Node<View, Edge> node ) {

                double parentX = 0;
                double parentY = 0;

                if ( null != parents && parents.hasNext() ) {

                    while ( parents.hasNext() ) {

                        Node tParent = parents.next();
                        final Object content = tParent.getContent();

                        if ( content instanceof View ) {
                            final View viewContent = ( View ) content;
                            final Double[] parentCoords = GraphUtils.getPosition( viewContent );
                            parentX += parentCoords[0];
                            parentY += parentCoords[1];
                        }

                    }

                }

                if ( isNodeAt( node, parentX, parentY, x, y ) ) {
                    result[0] = node;
                }

            }

            
        });

        return result[0];
    }

    private boolean isNodeAt( final Node node,
                              final double parentX,
                              final double parentY,
                              final double mouseX,
                              final double mouseY ) {

        if ( null != rootUUID && node.getUUID().equals( rootUUID ) ) {
            return true;
        }

        final View content = (View) node.getContent();
        final Bounds bounds = content.getBounds();
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

    @Override
    public GraphBoundsIndexer setRootUUID( final String uuid ) {
        this.rootUUID = uuid;
        return this;
    }
}
