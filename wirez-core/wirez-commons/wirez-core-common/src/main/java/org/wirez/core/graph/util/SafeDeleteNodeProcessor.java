package org.wirez.core.graph.util;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Graph;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.definition.Definition;
import org.wirez.core.graph.content.relationship.Child;
import org.wirez.core.graph.content.view.View;

import java.util.LinkedList;
import java.util.List;

public class SafeDeleteNodeProcessor {

    public interface DeleteNodeCallback {

        void deleteChildNode( final Node<Definition<?>, Edge> node );

        void deleteInViewEdge( final Edge<View<?>, Node> edge );

        void deleteInChildEdge( final Node parent, final Edge<Child, Node> edge );

        void deleteOutEdge( final Edge<? extends View<?>, Node> edge );

        void deleteNode( final Node<Definition<?>, Edge> node );

    }

    private final Node<Definition<?>, Edge> candidate;

    public SafeDeleteNodeProcessor( final Node<Definition<?>, Edge> candidate ) {
        this.candidate = candidate;
    }

    @SuppressWarnings( "unchecked" )
    public void run( final DeleteNodeCallback callback ) {

        // Check node's children, if any.
        final List<Node> children = getChildNodes( candidate );

        for ( Node child : children ) {

            callback.deleteChildNode( child );

        }

        // Check if is a child node, so if exists an ingoing child edge ( from parent node ).
        final List<Edge> inEdges = candidate.getInEdges();

        if ( null != inEdges && !inEdges.isEmpty() ) {

            for ( final Edge inEdge : inEdges ) {

                if ( inEdge.getContent() instanceof Child ) {

                    final Node parent = inEdge.getSourceNode();

                    callback.deleteInChildEdge( parent, inEdge );

                } else if ( inEdge.getContent() instanceof View ) {

                    callback.deleteInViewEdge( inEdge );

                }

            }

        }

        final List<Edge> outEdges = candidate.getOutEdges();

        if ( null != outEdges && !outEdges.isEmpty() ) {

            for ( final Edge outEdge : outEdges ) {

                if ( outEdge.getContent() instanceof View ) {

                    callback.deleteOutEdge( outEdge );

                }

            }

        }

        callback.deleteNode( candidate );

    }

    public void run( final Graph<?, Node> graph ) {

        this.run( new DeleteNodeCallback() {

            @Override
            public void deleteChildNode( final Node<Definition<?>, Edge> node ) {
                new SafeDeleteNodeProcessor( node ).run( graph );
            }

            @Override
            public void deleteInViewEdge( final Edge<View<?>, Node> edge ) {
                candidate.getInEdges().remove( edge );
            }

            @Override
            public void deleteInChildEdge( Node parent, Edge<Child, Node> edge ) {
                candidate.getInEdges().remove( edge );
            }

            @Override
            public void deleteOutEdge( Edge<? extends View<?>, Node> edge ) {
                candidate.getOutEdges().remove( edge );
            }

            @Override
            public void deleteNode( Node<Definition<?>, Edge> node ) {
                graph.removeNode( node.getUUID() );
            }

        } );

    }

    @SuppressWarnings( "unchecked" )
    private List<Node> getChildNodes(final Node node) {
        final List<Node> nodesToRemove = new LinkedList<>();
        final List<Edge<?, Node>> outEdges = node.getOutEdges();
        if ( null != outEdges && !outEdges.isEmpty() ) {
            for ( Edge<?, Node> outEdge : outEdges ) {
                if ( outEdge.getContent() instanceof Child) {
                    final Node target = outEdge.getTargetNode();
                    nodesToRemove.add( target );
                }
            }
        }

        return nodesToRemove;
    }

}
