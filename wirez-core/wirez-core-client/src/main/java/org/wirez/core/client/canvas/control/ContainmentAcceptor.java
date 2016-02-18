package org.wirez.core.client.canvas.control;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;

public interface ContainmentAcceptor {
    
    boolean allow(Node parent, Node child);

    boolean accept(Node parent, Node child);
    
}
