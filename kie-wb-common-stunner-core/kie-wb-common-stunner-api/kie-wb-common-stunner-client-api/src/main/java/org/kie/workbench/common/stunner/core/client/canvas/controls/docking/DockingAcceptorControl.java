package org.kie.workbench.common.stunner.core.client.canvas.controls.docking;

import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;
import org.kie.workbench.common.stunner.core.client.canvas.controls.CanvasControl;
import org.kie.workbench.common.stunner.core.graph.Node;

public interface DockingAcceptorControl<H extends CanvasHandler> extends CanvasControl<H> {
    
    boolean allow(Node parent, Node child);

    boolean accept(Node parent, Node child);
    
}
