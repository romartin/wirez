package org.kie.workbench.common.stunner.core.client.session;

import org.kie.workbench.common.stunner.core.client.canvas.Canvas;
import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;
import org.kie.workbench.common.stunner.core.client.canvas.controls.pan.PanControl;
import org.kie.workbench.common.stunner.core.client.canvas.controls.select.SelectionControl;
import org.kie.workbench.common.stunner.core.client.canvas.controls.zoom.ZoomControl;
import org.kie.workbench.common.stunner.core.graph.Element;

public interface CanvasReadOnlySession<C extends Canvas, H extends CanvasHandler> 
        extends CanvasSession<C, H> {

    SelectionControl<H, Element> getShapeSelectionControl();

    ZoomControl<C> getZoomControl();

    PanControl<C> getPanControl();
    
}
