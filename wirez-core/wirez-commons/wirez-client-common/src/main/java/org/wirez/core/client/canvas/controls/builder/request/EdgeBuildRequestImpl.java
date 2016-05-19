package org.wirez.core.client.canvas.controls.builder.request;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;

public final class EdgeBuildRequestImpl extends AbstractBuildRequest implements EdgeBuildRequest {
    
    private final Edge<View<?>, Node> edge;
    private Node<View<?>, Edge> inNode;
    private Node<View<?>, Edge> outNode;

    public EdgeBuildRequestImpl(final double x, 
                                final double y, 
                                final Edge<View<?>, Node> edge, 
                                final Node<View<?>, Edge> inNode, 
                                final Node<View<?>, Edge> outNode) {
        super(x, y);
        this.edge = edge;
        this.inNode = inNode;
        this.outNode = outNode;
    }

    public EdgeBuildRequestImpl(final double x,
                                final double y,
                                final Edge<View<?>, Node> edge) {
        super(x, y);
        this.edge = edge;
    }

    @Override
    public Edge<View<?>, Node> getEdge() {
        return edge;
    }

    @Override
    public Node<View<?>, Edge> getInNode() {
        return inNode;
    }

    @Override
    public Node<View<?>, Edge> getOutNode() {
        return outNode;
    }

}
