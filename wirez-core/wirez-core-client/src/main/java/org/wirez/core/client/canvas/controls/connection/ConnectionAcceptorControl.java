package org.wirez.core.client.canvas.controls.connection;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.controls.CanvasControl;

public interface ConnectionAcceptorControl<H extends CanvasHandler> extends CanvasControl<H> {
    
    boolean allowSource(Node source, Edge<View<?>, Node> connector, int magnet);

    boolean allowTarget(Node source, Edge<View<?>, Node> connector, int magnet);

    boolean acceptSource(Node source, Edge<View<?>, Node> connector, int magnet);

    boolean acceptTarget(Node source, Edge<View<?>, Node> connector, int magnet);
    
}
