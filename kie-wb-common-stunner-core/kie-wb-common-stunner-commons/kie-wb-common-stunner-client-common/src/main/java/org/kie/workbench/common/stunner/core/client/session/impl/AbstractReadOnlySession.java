package org.kie.workbench.common.stunner.core.client.session.impl;

import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvas;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.client.canvas.controls.pan.PanControl;
import org.kie.workbench.common.stunner.core.client.canvas.controls.select.SelectionControl;
import org.kie.workbench.common.stunner.core.client.canvas.controls.zoom.ZoomControl;
import org.kie.workbench.common.stunner.core.graph.Element;

public abstract class AbstractReadOnlySession extends AbstractCanvasSession 
        implements DefaultCanvasReadOnlySession {

    SelectionControl<AbstractCanvasHandler, Element> selectionControl;
    ZoomControl<AbstractCanvas> zoomControl;
    PanControl<AbstractCanvas> panControl;
    
    public AbstractReadOnlySession(final AbstractCanvas canvas,
                                   final AbstractCanvasHandler canvasHandler,
                                   final SelectionControl<AbstractCanvasHandler, Element> selectionControl,
                                   final ZoomControl<AbstractCanvas> zoomControl,
                                   final PanControl<AbstractCanvas> panControl) {
        super( canvas, canvasHandler );
        this.selectionControl = selectionControl;
        this.zoomControl = zoomControl;
        this.panControl = panControl;
    }

    @Override
    public SelectionControl<AbstractCanvasHandler, Element> getShapeSelectionControl() {
        return selectionControl;
    }

    @Override
    public ZoomControl<AbstractCanvas> getZoomControl() {
        return zoomControl;
    }

    @Override
    public PanControl<AbstractCanvas> getPanControl() {
        return panControl;
    }

    @Override
    public void doDispose() {
        
        if ( null != selectionControl ) {
            selectionControl.disable();
            selectionControl = null;
        }
        
        if ( null != zoomControl ) {
            zoomControl.disable();
            zoomControl = null;
        }

        if ( null != panControl ) {
            panControl.disable();
            panControl = null;
        }

    }
    
}
