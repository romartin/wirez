package org.wirez.core.client.canvas.controls;

import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.event.AbstractCanvasEvent;
import org.wirez.core.client.canvas.event.CanvasShapeAddedEvent;
import org.wirez.core.client.canvas.event.CanvasShapeRemovedEvent;
import org.wirez.core.client.shape.Shape;

import javax.enterprise.event.Observes;

public abstract class AbstractCanvasRegistrationControl extends AbstractCanvasControl 
        implements CanvasRegistationControl<AbstractCanvas, Shape> {
    
    void onCanvasShapeAdded(@Observes CanvasShapeAddedEvent canvasShapeAddedEvent) {
        if ( checkEventContext(canvasShapeAddedEvent) ) {
            this.register( canvasShapeAddedEvent.getShape() );
        }
    }

    void onCanvasShapeRemoved(@Observes CanvasShapeRemovedEvent canvasShapeRemovedEvent) {
        if ( checkEventContext(canvasShapeRemovedEvent) ) {
            this.deregister( canvasShapeRemovedEvent.getShape() );
        }
    }
    
    protected boolean checkEventContext(final AbstractCanvasEvent canvasEvent) {
        final Canvas _canvas = canvasEvent.getCanvas();
        return canvas != null && canvas.equals(_canvas);
    }
    
}
