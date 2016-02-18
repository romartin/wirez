package org.wirez.core.client.canvas.control;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.client.canvas.CanvasHandler;

public interface ConnectionAcceptor<H extends CanvasHandler> {
    
    boolean allowSource(H canvasHandler, Node source, Edge<ViewContent<?>, Node> connector, int magnet);

    boolean allowTarget(H canvasHandler, Node source, Edge<ViewContent<?>, Node> connector, int magnet);

    boolean acceptSource(H canvasHandler, Node source, Edge<ViewContent<?>, Node> connector, int magnet);

    boolean acceptTarget(H canvasHandler, Node source, Edge<ViewContent<?>, Node> connector, int magnet);
    
}
