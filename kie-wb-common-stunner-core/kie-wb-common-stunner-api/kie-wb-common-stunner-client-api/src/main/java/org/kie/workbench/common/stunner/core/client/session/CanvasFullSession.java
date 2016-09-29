package org.kie.workbench.common.stunner.core.client.session;

import org.kie.workbench.common.stunner.core.client.canvas.controls.actions.CanvasNameEditionControl;
import org.kie.workbench.common.stunner.core.client.canvas.controls.actions.CanvasSaveControl;
import org.kie.workbench.common.stunner.core.client.canvas.controls.actions.CanvasValidationControl;
import org.kie.workbench.common.stunner.core.client.canvas.controls.palette.CanvasPaletteControl;
import org.kie.workbench.common.stunner.core.client.canvas.controls.resize.ResizeControl;
import org.kie.workbench.common.stunner.core.graph.Element;
import org.kie.workbench.common.stunner.core.client.canvas.Canvas;
import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;
import org.kie.workbench.common.stunner.core.client.command.CanvasCommandManager;
import org.kie.workbench.common.stunner.core.client.canvas.controls.builder.ElementBuilderControl;
import org.kie.workbench.common.stunner.core.client.canvas.controls.connection.ConnectionAcceptorControl;
import org.kie.workbench.common.stunner.core.client.canvas.controls.containment.ContainmentAcceptorControl;
import org.kie.workbench.common.stunner.core.client.canvas.controls.docking.DockingAcceptorControl;
import org.kie.workbench.common.stunner.core.client.canvas.controls.drag.DragControl;
import org.kie.workbench.common.stunner.core.client.canvas.controls.toolbox.ToolboxControl;

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
