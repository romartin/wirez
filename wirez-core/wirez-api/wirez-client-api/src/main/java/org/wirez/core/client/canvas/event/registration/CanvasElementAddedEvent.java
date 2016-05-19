package org.wirez.core.client.canvas.event.registration;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.graph.Element;

@NonPortable
public final class CanvasElementAddedEvent extends AbstractCanvasHandlerElementEvent {

    public CanvasElementAddedEvent(final CanvasHandler canvasHandler, 
                                   final Element<?> element) {
        super(canvasHandler, element);
    }
    
}
