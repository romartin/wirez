package org.wirez.core.client.canvas.controls;

import org.wirez.core.graph.Element;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.event.AbstractCanvasHandlerEvent;
import org.wirez.core.client.canvas.event.registration.CanvasElementAddedEvent;
import org.wirez.core.client.canvas.event.registration.CanvasElementRemovedEvent;
import org.wirez.core.client.canvas.event.registration.CanvasElementUpdatedEvent;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.view.HasEventHandlers;
import org.wirez.core.client.shape.view.event.ViewHandler;

import javax.enterprise.event.Observes;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCanvasHandlerRegistrationControl extends AbstractCanvasHandlerControl
        implements CanvasRegistationControl<AbstractCanvasHandler, Element> {

    private final Map<String, ViewHandler<?>> handlers = new HashMap<>();

    public void update(final Element element) {
        // Do nothing by default.
    }
    
    protected void registerHandler( String uuid, ViewHandler<?> handler ) {
        handlers.put( uuid, handler );
    }


    @Override
    protected void doDisable() {

        // De-register all drag handlers.
        for( Map.Entry<String, ViewHandler<?>> entry : handlers.entrySet() ) {
            final String uuid = entry.getKey();
            final Shape shape = canvasHandler.getCanvas().getShape( uuid );
            final ViewHandler<?> handler = entry.getValue();
            doDeregister(shape, handler);
        }
        
    }

    @Override
    public void deregister(Element element) {

        final Shape<?> shape = (Shape<?>) canvasHandler.getCanvas().getShape( element.getUUID() );
        if ( null != shape ) {
            ViewHandler<?> handler = handlers.get( element.getUUID() );
            doDeregister(shape, handler);
            handlers.remove( element.getUUID() );
        }
        
    }

    protected void doDeregister(final Shape shape,
                                final ViewHandler<?> handler) {

        if (null != handler) {
            final HasEventHandlers hasEventHandlers = (HasEventHandlers) shape.getShapeView();
            hasEventHandlers.removeHandler(handler);
        }

    }
    

    void onCanvasElementAddedEvent(@Observes CanvasElementAddedEvent canvasElementAddedEvent) {
        if ( checkEventContext(canvasElementAddedEvent) ) {
            this.register( canvasElementAddedEvent.getElement() );
        }
    }

    void onCanvasElementRemovedEvent(@Observes CanvasElementRemovedEvent elementRemovedEvent) {
        if ( checkEventContext(elementRemovedEvent) ) {
            this.deregister( elementRemovedEvent.getElement() );
        }
    }

    void onCanvasElementUpdatedEvent(@Observes CanvasElementUpdatedEvent elementUpdatedEvent) {
        if ( checkEventContext(elementUpdatedEvent) ) {
            this.update( elementUpdatedEvent.getElement() );
        }
    }
    
    protected boolean checkEventContext(final AbstractCanvasHandlerEvent canvasHandlerEvent) {
        final CanvasHandler _canvasHandler = canvasHandlerEvent.getCanvasHandler();
        return canvasHandler != null && canvasHandler.equals(_canvasHandler);
    }
    
}
