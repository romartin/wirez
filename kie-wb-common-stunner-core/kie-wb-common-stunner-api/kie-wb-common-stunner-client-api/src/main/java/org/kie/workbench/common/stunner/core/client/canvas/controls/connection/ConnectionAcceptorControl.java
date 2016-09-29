package org.kie.workbench.common.stunner.core.client.canvas.controls.connection;

import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.view.View;
import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;
import org.kie.workbench.common.stunner.core.client.canvas.controls.CanvasControl;

public interface ConnectionAcceptorControl<H extends CanvasHandler> extends CanvasControl<H> {
    
    boolean allowSource(Node source, Edge<View<?>, Node> connector, int magnet);

    boolean allowTarget(Node source, Edge<View<?>, Node> connector, int magnet);

    boolean acceptSource(Node source, Edge<View<?>, Node> connector, int magnet);

    boolean acceptTarget(Node source, Edge<View<?>, Node> connector, int magnet);
    
}
