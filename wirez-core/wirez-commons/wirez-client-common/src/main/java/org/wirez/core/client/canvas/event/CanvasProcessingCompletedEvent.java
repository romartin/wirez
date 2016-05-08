package org.wirez.core.client.canvas.event;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.wirez.core.client.canvas.CanvasHandler;

@NonPortable
public final class CanvasProcessingCompletedEvent extends AbstractCanvasHandlerEvent {

    public CanvasProcessingCompletedEvent(final CanvasHandler canvasHandler ) {
        super( canvasHandler );
    }
    
}
