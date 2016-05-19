package org.wirez.core.client.canvas.controls.builder.request;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;

public final class NodeBuildRequestImpl extends AbstractBuildRequest implements NodeBuildRequest {
    
    private final Node<View<?>, Edge> node;
    private Edge<View<?>, Node> inEdge;
    
    public NodeBuildRequestImpl(final double x, 
                                final double y, 
                                final Node<View<?>, Edge> node) {
        super(x, y);
        this.node = node;
    }

    public NodeBuildRequestImpl(final double x,
                                final double y,
                                final Node<View<?>, Edge> node,
                                final Edge<View<?>, Node> inEdge) {
        super(x, y);
        this.node = node;
        this.inEdge = inEdge;
    }

    @Override
    public Node<View<?>, Edge> getNode() {
        return node;
    }

    @Override
    public Edge<View<?>, Node> getInEdge() {
        return inEdge;
    }

}
