package org.kie.workbench.common.stunner.core.client.session.impl;

import org.kie.workbench.common.stunner.core.client.api.platform.Desktop;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvas;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.client.canvas.controls.pan.PanControl;
import org.kie.workbench.common.stunner.core.client.canvas.controls.select.SelectionControl;
import org.kie.workbench.common.stunner.core.client.canvas.controls.zoom.Wheel;
import org.kie.workbench.common.stunner.core.client.canvas.controls.zoom.ZoomControl;
import org.kie.workbench.common.stunner.core.graph.Element;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class CanvasReadOnlySessionImpl extends AbstractReadOnlySession {

    @Inject
    public CanvasReadOnlySessionImpl(final AbstractCanvas canvas, 
                                     final AbstractCanvasHandler canvasHandler,
                                     final @Desktop  SelectionControl<AbstractCanvasHandler, Element> selectionControl,
                                     final @Wheel ZoomControl<AbstractCanvas> zoomControl,
                                     final PanControl<AbstractCanvas> panControl) {
        
        super( canvas, canvasHandler, selectionControl, zoomControl, panControl );
        
    }

    @Override
    public String toString() {
        return "CanvasReadOnlySessionImpl";
    }
    
}
