package org.wirez.core.client.canvas.controls;

import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.event.AbstractCanvasEvent;
import org.wirez.core.client.canvas.event.registration.CanvasShapeAddedEvent;
import org.wirez.core.client.canvas.event.registration.CanvasShapeRemovedEvent;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.view.HasEventHandlers;
import org.wirez.core.client.shape.view.event.ViewHandler;

import javax.enterprise.event.Observes;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCanvasRegistrationControl extends AbstractCanvasControl 
        implements CanvasRegistationControl<AbstractCanvas, Shape> {

    private final Map<String, ViewHandler<?>> handlers = new HashMap<>();

    protected void registerHandler( String uuid, ViewHandler<?> handler ) {
        handlers.put( uuid, handler );
    }

    @Override
    protected void doDisable() {
        
        // De-register all drag handlers.
        for( Map.Entry<String, ViewHandler<?>> entry : handlers.entrySet() ) {
            final String uuid = entry.getKey();
            final Shape shape = canvas.getShape( uuid );
            final ViewHandler<?> handler = entry.getValue();
            doDeregister(shape, handler);
        }
        
    }

    @Override
    public void deregister( final Shape shape ) {

        if ( null != shape ) {
            ViewHandler<?> handler = handlers.get( shape.getUUID() );
            doDeregister(shape, handler);
        }

    }

    protected void doDeregister(final Shape shape,
                                final ViewHandler<?> handler) {

        if (null != handler) {
            final HasEventHandlers hasEventHandlers = (HasEventHandlers) shape.getShapeView();
            hasEventHandlers.removeHandler(handler);
        }

    }
    
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
