package org.wirez.core.client.canvas.control;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;

public interface ConnectionAcceptor {
    
    boolean allowSource(Node source, Edge<ViewContent<?>, Node> connector, int magnet);

    boolean allowTarget(Node source, Edge<ViewContent<?>, Node> connector, int magnet);

    boolean acceptSource(Node source, Edge<ViewContent<?>, Node> connector, int magnet);

    boolean acceptTarget(Node source, Edge<ViewContent<?>, Node> connector, int magnet);
    
}
