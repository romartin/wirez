package org.kie.workbench.common.stunner.core.graph.processing.index.bounds;

import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Graph;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.relationship.Child;
import org.kie.workbench.common.stunner.core.graph.content.Bounds;
import org.kie.workbench.common.stunner.core.graph.content.view.View;
import org.kie.workbench.common.stunner.core.graph.processing.traverse.content.AbstractChildrenTraverseCallback;
import org.kie.workbench.common.stunner.core.graph.util.GraphUtils;
import org.kie.workbench.common.stunner.core.graph.processing.traverse.content.ChildrenTraverseProcessor;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Iterator;

@Dependent
public class GraphBoundsIndexerImpl implements GraphBoundsIndexer {

    private Graph<View, Node<View, Edge>> graph;
    private String rootUUID = null;


    ChildrenTraverseProcessor childrenTraverseProcessor;

    @Inject
    public GraphBoundsIndexerImpl( final ChildrenTraverseProcessor childrenTraverseProcessor ) {
        this.childrenTraverseProcessor = childrenTraverseProcessor;
    }

    @Override
    public GraphBoundsIndexerImpl build( Graph<View, Node<View, Edge>> graph ) {
        this.graph = graph;
        return this;
    }

    @Override
    public Node<View<?>, Edge> getAt( final double x,
                                      final double y ) {
        return findElementAt( x, y );
    }

    @Override
    public double[] getTrimmedBounds() {
        final double[] result = new double[] { Double.MAX_VALUE, Double.MAX_VALUE, 0, 0 };

        childrenTraverseProcessor
                .setRootUUID( this.rootUUID )
                .traverse( graph, new GraphBoundIndexerTraverseCallback( new NodeBoundsTraverseCallback() {

            @Override
            public void onNodeTraverse( final Node<View, Edge> node,
                                        final double parentX,
                                        final double parentY ) {

                final String uuid = node.getUUID();

                final boolean isRoot = null != GraphBoundsIndexerImpl.this.rootUUID
                        && GraphBoundsIndexerImpl.this.rootUUID.equals( uuid );

                if ( !isRoot ) {

                    final double[] absCoords = getNodeAbsoluteCoordinates( node, parentX, parentY );

                    final double x = absCoords[0];
                    final double y = absCoords[1];
                    final double w = absCoords[2];
                    final double h = absCoords[3];

                    if ( x < result[0] ) {

                        result[0] = x;

                    }

                    if ( y < result[1] ) {

                        result[1] = y;

                    }

                    if ( w > result[2] ) {

                        result[2] = w;

                    }

                    if ( h > result[3] ) {

                        result[3] = h;

                    }

                }

            }

        } ) );

        return result;

    }

    public Node<View<?>, Edge> findElementAt( final double x, final double y ) {
        final Node[] result = new Node[1];

        childrenTraverseProcessor.traverse( graph, new GraphBoundIndexerTraverseCallback( new NodeBoundsTraverseCallback() {

            @Override
            public void onNodeTraverse( final Node<View, Edge> node,
                                        final double parentX,
                                        final double parentY ) {

                if ( isNodeAt( node, parentX, parentY, x, y ) ) {
                    result[0] = node;
                }

            }

        } ) );

        return result[0];
    }

    private abstract class NodeBoundsTraverseCallback {

        public boolean onNodeTraverseStart( Node<View, Edge> node, Iterator<Node<View, Edge>> parents ) {
            return true;
        }

        public abstract void onNodeTraverse( Node<View, Edge> node, double parentX, double parentY );

    }

    private class GraphBoundIndexerTraverseCallback extends AbstractChildrenTraverseCallback<Node<View, Edge>, Edge<Child, Node>> {

        private final NodeBoundsTraverseCallback callback;

        private GraphBoundIndexerTraverseCallback( final NodeBoundsTraverseCallback callback ) {
            this.callback = callback;
        }


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

            if ( callback.onNodeTraverseStart( node, parents ) ) {

                double parentX = 0;
                double parentY = 0;

                if ( null != parents && parents.hasNext() ) {

                    while ( parents.hasNext() ) {

                        final Node tParent = parents.next();

                        final Double[] nodeCoordinates = getNodeCoordinates( tParent );

                        if ( null != nodeCoordinates ) {

                            parentX += nodeCoordinates[0];
                            parentY += nodeCoordinates[1];

                        }

                    }

                }

                callback.onNodeTraverse( node, parentX, parentY );

            }

        }

    }

    private Double[] getNodeCoordinates( final Node node ) {

        if ( null != node ) {

            final Object content = node.getContent();

            if ( content instanceof View ) {

                final View viewContent = ( View ) content;
                return GraphUtils.getPosition( viewContent );
            }

        }

        return null;
    }

    private Double[] getNodeCoordinates( final Node node,
                                         final String root ) {

        if ( null != node ) {

            final String uuid = node.getUUID();

            if ( null == root || !root.equals( uuid ) ) {

                return getNodeCoordinates( node );

            }

        }

        return null;
    }

    private double[] getNodeAbsoluteCoordinates( final Node node,
                                    final double parentX,
                                    final double parentY ) {

        final View content = (View) node.getContent();
        final Bounds bounds = content.getBounds();
        final Bounds.Bound ulBound = bounds.getUpperLeft();
        final Bounds.Bound lrBound = bounds.getLowerRight();
        final double ulX = ulBound.getX() + parentX;
        final double ulY = ulBound.getY() + parentY;
        final double lrX = lrBound.getX() + parentX;
        final double lrY = lrBound.getY() + parentY;

        return new double[] { ulX, ulY, lrX, lrY };
    }

    private boolean isNodeAt( final Node node,
                              final double parentX,
                              final double parentY,
                              final double mouseX,
                              final double mouseY ) {

        if ( null != rootUUID && node.getUUID().equals( rootUUID ) ) {
            return true;
        }

        final double[] absoluteCoords = getNodeAbsoluteCoordinates( node, parentX, parentY );
        final double ulX = absoluteCoords[0];
        final double ulY = absoluteCoords[1];
        final double lrX = absoluteCoords[2];
        final double lrY = absoluteCoords[3];

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

    @Override
    public void destroy() {
        this.graph = null;
        this.rootUUID = null;
        this.childrenTraverseProcessor = null;
    }

}
