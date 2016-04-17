package org.wirez.core.client.canvas.controls;

import org.wirez.core.api.graph.Element;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.event.AbstractCanvasHandlerEvent;
import org.wirez.core.client.canvas.event.CanvasElementAddedEvent;
import org.wirez.core.client.canvas.event.CanvasElementRemovedEvent;

import javax.enterprise.event.Observes;

public abstract class AbstractCanvasHandlerRegistrationControl extends AbstractCanvasHandlerControl
        implements CanvasRegistationControl<AbstractCanvasHandler, Element> {

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
    
    protected boolean checkEventContext(final AbstractCanvasHandlerEvent canvasHandlerEvent) {
        final CanvasHandler _canvasHandler = canvasHandlerEvent.getCanvasHandler();
        return canvasHandler != null && canvasHandler.equals(_canvasHandler);
    }
    
}
