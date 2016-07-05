package org.wirez.core.client.session;

import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.controls.pan.PanControl;
import org.wirez.core.client.canvas.controls.select.SelectionControl;
import org.wirez.core.client.canvas.controls.zoom.ZoomControl;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.graph.Element;

public interface CanvasReadOnlySession<C extends Canvas, H extends CanvasHandler> 
        extends CanvasSession<C, H> {

    SelectionControl<H, Element> getShapeSelectionControl();

    ZoomControl<C> getZoomControl();

    PanControl<C> getPanControl();
    
}
