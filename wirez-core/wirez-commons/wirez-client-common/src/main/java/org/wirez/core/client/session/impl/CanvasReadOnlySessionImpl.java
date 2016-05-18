package org.wirez.core.client.session.impl;

import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.controls.pan.PanControl;
import org.wirez.core.client.canvas.controls.select.SelectionControl;
import org.wirez.core.client.canvas.controls.zoom.Wheel;
import org.wirez.core.client.canvas.controls.zoom.ZoomControl;
import org.wirez.core.client.shape.Shape;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class CanvasReadOnlySessionImpl extends AbstractCanvasSession 
        implements DefaultCanvasReadOnlySession {

    SelectionControl<AbstractCanvas, Shape> selectionControl;
    ZoomControl<AbstractCanvas> zoomControl;
    PanControl<AbstractCanvas> panControl;
    
    @Inject
    public CanvasReadOnlySessionImpl(final AbstractCanvas canvas, 
                                     final AbstractCanvasHandler canvasHandler,
                                     final SelectionControl<AbstractCanvas, Shape> selectionControl,
                                     final @Wheel ZoomControl<AbstractCanvas> zoomControl,
                                     final PanControl<AbstractCanvas> panControl) {
        super(canvas, canvasHandler);
        this.selectionControl = selectionControl;
        this.zoomControl = zoomControl;
        this.panControl = panControl;
    }

    @Override
    public SelectionControl<AbstractCanvas, Shape> getShapeSelectionControl() {
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
    public void onDispose() {
        
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

        super.onDispose();

    }
}
