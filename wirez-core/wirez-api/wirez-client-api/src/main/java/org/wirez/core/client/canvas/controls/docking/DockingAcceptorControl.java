package org.wirez.core.client.canvas.controls.docking;

import org.wirez.core.api.graph.Node;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.controls.CanvasControl;

public interface DockingAcceptorControl<H extends CanvasHandler> extends CanvasControl<H> {
    
    boolean allow(Node parent, Node child);

    boolean accept(Node parent, Node child);
    
}
