package org.wirez.core.client.session;

import org.wirez.core.client.canvas.controls.actions.CanvasNameEditionControl;
import org.wirez.core.client.canvas.controls.actions.CanvasSaveControl;
import org.wirez.core.client.canvas.controls.actions.CanvasValidationControl;
import org.wirez.core.client.canvas.controls.palette.CanvasPaletteControl;
import org.wirez.core.client.canvas.controls.resize.ResizeControl;
import org.wirez.core.graph.Element;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.command.CanvasCommandManager;
import org.wirez.core.client.canvas.controls.builder.ElementBuilderControl;
import org.wirez.core.client.canvas.controls.connection.ConnectionAcceptorControl;
import org.wirez.core.client.canvas.controls.containment.ContainmentAcceptorControl;
import org.wirez.core.client.canvas.controls.docking.DockingAcceptorControl;
import org.wirez.core.client.canvas.controls.drag.DragControl;
import org.wirez.core.client.canvas.controls.toolbox.ToolboxControl;

public interface CanvasFullSession<C extends Canvas, H extends CanvasHandler> 
    extends CanvasReadOnlySession<C, H> {

    ResizeControl<H, Element> getResizeControl();

    CanvasValidationControl<H> getCanvasValidationControl();

    CanvasSaveControl<H> getCanvasSaveControl();

    CanvasPaletteControl<H> getCanvasPaletteControl();

    CanvasCommandManager<H> getCanvasCommandManager();
    
    ConnectionAcceptorControl<H> getConnectionAcceptorControl();
    
    ContainmentAcceptorControl<H> getContainmentAcceptorControl();
    
    DockingAcceptorControl<H> getDockingAcceptorControl();

    CanvasNameEditionControl<H, Element> getCanvasNameEditionControl();

    DragControl<H, Element> getDragControl();
    
    ToolboxControl<H, Element> getToolboxControl();
    
    ElementBuilderControl<H> getBuilderControl();
    
    
    
}
