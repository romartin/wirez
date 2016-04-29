package org.wirez.core.client.session.impl;

import org.wirez.core.api.graph.Element;
import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.canvas.controls.builder.BuilderControl;
import org.wirez.core.client.canvas.controls.connection.ConnectionAcceptorControl;
import org.wirez.core.client.canvas.controls.containment.ContainmentAcceptorControl;
import org.wirez.core.client.canvas.controls.docking.DockingAcceptorControl;
import org.wirez.core.client.canvas.controls.drag.DragControl;
import org.wirez.core.client.canvas.controls.pan.PanControl;
import org.wirez.core.client.canvas.controls.select.SelectionControl;
import org.wirez.core.client.canvas.controls.toolbox.ToolboxControl;
import org.wirez.core.client.canvas.controls.zoom.Wheel;
import org.wirez.core.client.canvas.controls.zoom.ZoomControl;
import org.wirez.core.client.shape.Shape;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class CanvasFullSessionImpl extends CanvasReadOnlySessionImpl 
        implements DefaultCanvasFullSession {

    CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager;
    ConnectionAcceptorControl<AbstractCanvasHandler> connectionAcceptorControl;
    ContainmentAcceptorControl<AbstractCanvasHandler> containmentAcceptorControl;
    DockingAcceptorControl<AbstractCanvasHandler> dockingAcceptorControl;
    DragControl<AbstractCanvasHandler, Element> dragControl;
    ToolboxControl<AbstractCanvasHandler, Element> toolboxControl;
    BuilderControl<AbstractCanvasHandler> builderControl;
    
    
    @Inject
    public CanvasFullSessionImpl(final AbstractCanvas canvas,
                                 final AbstractCanvasHandler canvasHandler,
                                 final CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager,
                                 final ConnectionAcceptorControl<AbstractCanvasHandler> connectionAcceptorControl,
                                 final ContainmentAcceptorControl<AbstractCanvasHandler> containmentAcceptorControl,
                                 final DockingAcceptorControl<AbstractCanvasHandler> dockingAcceptorControl,
                                 final SelectionControl<AbstractCanvas, Shape> selectionControl,
                                 final DragControl<AbstractCanvasHandler, Element> dragControl,
                                 final ToolboxControl<AbstractCanvasHandler, Element> toolboxControl,
                                 final BuilderControl<AbstractCanvasHandler> builderControl,
                                 final @Wheel ZoomControl<AbstractCanvas> zoomControl,
                                 final PanControl<AbstractCanvas> panControl) {
        super(canvas, canvasHandler, selectionControl, zoomControl, panControl);
        this.canvasCommandManager = canvasCommandManager;
        this.connectionAcceptorControl = connectionAcceptorControl;
        this.containmentAcceptorControl = containmentAcceptorControl;
        this.dockingAcceptorControl = dockingAcceptorControl;
        this.dragControl = dragControl;
        this.toolboxControl = toolboxControl;
        this.builderControl = builderControl;

    }


    @Override
    public CanvasCommandManager<AbstractCanvasHandler> getCanvasCommandManager() {
        return canvasCommandManager;
    }

    @Override
    public ConnectionAcceptorControl<AbstractCanvasHandler> getConnectionAcceptorControl() {
        return connectionAcceptorControl;
    }

    @Override
    public ContainmentAcceptorControl<AbstractCanvasHandler> getContainmentAcceptorControl() {
        return containmentAcceptorControl;
    }

    @Override
    public DockingAcceptorControl<AbstractCanvasHandler> getDockingAcceptorControl() {
        return dockingAcceptorControl;
    }

    @Override
    public DragControl<AbstractCanvasHandler, Element> getDragControl() {
        return dragControl;
    }

    @Override
    public ToolboxControl<AbstractCanvasHandler, Element> getToolboxControl() {
        return toolboxControl;
    }

    @Override
    public BuilderControl<AbstractCanvasHandler> getBuilderControl() {
        return builderControl;
    }

    @Override
    public ZoomControl<AbstractCanvas> getZoomControl() {
        return zoomControl;
    }

    @Override
    public PanControl<AbstractCanvas> getPanControl() {
        return panControl;
    }

}
