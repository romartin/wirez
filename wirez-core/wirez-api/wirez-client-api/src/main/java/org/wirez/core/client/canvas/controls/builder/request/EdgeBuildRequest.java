package org.wirez.core.client.canvas.controls.builder.request;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.client.canvas.controls.builder.BuildRequest;

public interface EdgeBuildRequest extends BuildRequest {
    
    Edge<View<?>, Node> getEdge();
    
    Node<View<?>, Edge> getInNode();

    Node<View<?>, Edge> getOutNode();
    
}
