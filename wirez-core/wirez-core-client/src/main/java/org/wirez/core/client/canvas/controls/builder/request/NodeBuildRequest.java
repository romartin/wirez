package org.wirez.core.client.canvas.controls.builder.request;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.client.canvas.controls.builder.BuildRequest;

public interface NodeBuildRequest extends BuildRequest {
    
    Node<View<?>, Edge> getNode();
    
    Edge<View<?>, Node> getInEdge();
    
}
