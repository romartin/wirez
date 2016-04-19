package org.wirez.core.client.session;

import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.controls.select.SelectionControl;
import org.wirez.core.client.shape.Shape;

public interface CanvasReadOnlySession<C extends Canvas, H extends CanvasHandler> 
        extends CanvasSession<C, H> {

    SelectionControl<C, Shape> getShapeSelectionControl();
    
}
