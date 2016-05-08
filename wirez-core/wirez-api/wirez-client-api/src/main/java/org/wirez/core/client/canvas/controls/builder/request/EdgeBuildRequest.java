package org.wirez.core.client.canvas.controls.builder.request;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.client.canvas.controls.builder.BuildRequest;

public interface EdgeBuildRequest extends BuildRequest {
    
    Edge<View<?>, Node> getEdge();
    
    Node<View<?>, Edge> getInNode();

    Node<View<?>, Edge> getOutNode();
    
}
