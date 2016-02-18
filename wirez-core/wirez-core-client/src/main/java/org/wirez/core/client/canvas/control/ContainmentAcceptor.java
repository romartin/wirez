package org.wirez.core.client.canvas.control;

import org.wirez.core.api.graph.Node;
import org.wirez.core.client.canvas.CanvasHandler;

public interface ContainmentAcceptor<H extends CanvasHandler>  {
    
    boolean allow(H canvasHandler, Node parent, Node child);

    boolean accept(H canvasHandler, Node parent, Node child);
    
}
