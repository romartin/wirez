package org.wirez.core.client.session.impl;

import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.controls.select.SelectionControl;
import org.wirez.core.client.shape.Shape;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class CanvasReadOnlySessionImpl extends AbstractCanvasSession 
        implements DefaultCanvasReadOnlySession {

    SelectionControl<AbstractCanvas, Shape> selectionControl;
    
    @Inject
    public CanvasReadOnlySessionImpl(final AbstractCanvas canvas, 
                                     final AbstractCanvasHandler canvasHandler,
                                     final SelectionControl<AbstractCanvas, Shape> selectionControl) {
        super(canvas, canvasHandler);
        this.selectionControl = selectionControl;
    }

    @Override
    public SelectionControl<AbstractCanvas, Shape> getShapeSelectionControl() {
        return selectionControl;
    }
    
}
