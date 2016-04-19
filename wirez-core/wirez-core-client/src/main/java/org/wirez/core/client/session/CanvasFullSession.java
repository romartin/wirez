package org.wirez.core.client.session;

import org.wirez.core.api.graph.Element;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.canvas.controls.connection.ConnectionAcceptorControl;
import org.wirez.core.client.canvas.controls.containment.ContainmentAcceptorControl;
import org.wirez.core.client.canvas.controls.drag.DragControl;
import org.wirez.core.client.canvas.controls.toolbox.ToolboxControl;

public interface CanvasFullSession<C extends Canvas, H extends CanvasHandler> 
    extends CanvasReadOnlySession<C, H> {

    CanvasCommandManager<H> getCanvasCommandManager();
    
    ConnectionAcceptorControl<H> getConnectionAcceptorControl();
    
    ContainmentAcceptorControl<H> getContainmentAcceptorControl();

    DragControl<H, Element> getDragControl();
    
    ToolboxControl<H, Element> getToolboxControl();
    
}
