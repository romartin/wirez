package org.wirez.core.client.canvas.util;

import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.Graph;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.relationship.Child;
import org.wirez.core.graph.content.view.Bounds;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.graph.processing.traverse.content.AbstractChildrenTraverseCallback;
import org.wirez.core.graph.processing.traverse.content.ChildrenTraverseProcessorImpl;
import org.wirez.core.graph.processing.traverse.tree.TreeWalkTraverseProcessorImpl;
import org.wirez.core.graph.util.GraphUtils;

import javax.enterprise.context.Dependent;
import java.util.Iterator;

// TODO: This has to be refactored by the use of a good canvas layout API.
// TODO: Pending take into account the diagram/graph size, currently it's always using am horizontal layout.
@Dependent
public class CanvasLayoutUtils {

    public double[] getNextLayoutPosition( final CanvasHandler canvasHandler, final Element<View<?>> source ) {

        final Double[] pos = GraphUtils.getPosition( source.getContent() );
        final Double[] size = GraphUtils.getSize( source.getContent() );

        return new double[] { pos[0] + size[0] + 50, pos[1] };
    }

    public double[] getNextLayoutPosition( final CanvasHandler canvasHandler ) {
        final String ruuid = canvasHandler.getDiagram().getSettings().getCanvasRootUUID();
        return getNextLayoutPosition( canvasHandler, ruuid );
    }

    @SuppressWarnings( "unchecked" )
    private double[] getNextLayoutPosition( final CanvasHandler canvasHandler, final String rootUUID ) {

        final Graph graph = canvasHandler.getDiagram().getGraph();

        final double[] result = new double[2];
        result[0] = 0;
        result[1] = 0;

        new ChildrenTraverseProcessorImpl( new TreeWalkTraverseProcessorImpl() )
                .setRootUUID( rootUUID )
                .traverse( graph, new AbstractChildrenTraverseCallback<Node<View, Edge>, Edge<Child, Node>>() {

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

                    final double[] coordinates = getNodeCoordinates( node, parentX, parentY );

                    if ( coordinates[0] > getCurrentMaxX() ) {

                        result[0] = coordinates[0];

                    }

                    if ( coordinates[1] > getCurrentMaxY() ) {

                        result[1] = coordinates[1];

                    }

                }



            }

            private double getCurrentMaxX() {
                return result[0];
            }

            private double getCurrentMaxY() {
                return result[1];
            }

        });

        return result;
    }

    private double[] getNodeCoordinates( final Node node,
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

        return new double[] { lrX, lrY };
    }

}
