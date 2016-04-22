package org.wirez.core.client.canvas.event;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.wirez.core.client.canvas.CanvasHandler;

@NonPortable
public final class CanvasProcessingStartedEvent extends AbstractCanvasHandlerEvent {

    public CanvasProcessingStartedEvent( final CanvasHandler canvasHandler ) {
        super( canvasHandler );
    }
    
}
