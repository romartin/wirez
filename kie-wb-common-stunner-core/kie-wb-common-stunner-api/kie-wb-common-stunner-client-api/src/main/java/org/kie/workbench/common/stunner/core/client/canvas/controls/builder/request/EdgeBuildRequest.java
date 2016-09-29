package org.kie.workbench.common.stunner.core.client.canvas.controls.builder.request;

import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.view.View;
import org.kie.workbench.common.stunner.core.client.canvas.controls.builder.BuildRequest;

public interface EdgeBuildRequest extends BuildRequest {
    
    Edge<View<?>, Node> getEdge();
    
    Node<View<?>, Edge> getInNode();

    Node<View<?>, Edge> getOutNode();
    
}
