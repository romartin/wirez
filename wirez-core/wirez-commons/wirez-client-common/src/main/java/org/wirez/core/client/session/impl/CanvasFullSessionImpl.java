package org.wirez.core.client.session.impl;

import org.wirez.core.client.api.platform.Desktop;
import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.canvas.controls.builder.ElementBuilderControl;
import org.wirez.core.client.canvas.controls.builder.impl.Observer;
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
import org.wirez.core.graph.Element;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class CanvasFullSessionImpl extends AbstractFullSession {

    @Inject
    public CanvasFullSessionImpl(final AbstractCanvas canvas,
                                 final AbstractCanvasHandler canvasHandler,
                                 final CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager,
                                 final ConnectionAcceptorControl<AbstractCanvasHandler> connectionAcceptorControl,
                                 final ContainmentAcceptorControl<AbstractCanvasHandler> containmentAcceptorControl,
                                 final DockingAcceptorControl<AbstractCanvasHandler> dockingAcceptorControl,
                                 final @Desktop SelectionControl<AbstractCanvas, Shape> selectionControl,
                                 final DragControl<AbstractCanvasHandler, Element> dragControl,
                                 final ToolboxControl<AbstractCanvasHandler, Element> toolboxControl,
                                 final @Observer  ElementBuilderControl<AbstractCanvasHandler> builderControl,
                                 final @Wheel ZoomControl<AbstractCanvas> zoomControl,
                                 final PanControl<AbstractCanvas> panControl) {
        
        super( canvas, canvasHandler, selectionControl, zoomControl, panControl, 
                canvasCommandManager, connectionAcceptorControl, containmentAcceptorControl, dockingAcceptorControl,
                dragControl, toolboxControl, builderControl );
        
    }

    @Override
    public String toString() {
        return "CanvasFullSessionImpl";
    }
    
}