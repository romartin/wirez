package org.wirez.core.client.canvas.event;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.wirez.core.client.canvas.CanvasHandler;

@NonPortable
public final class CanvasInitializationCompletedEvent extends AbstractCanvasHandlerEvent {

    public CanvasInitializationCompletedEvent(final CanvasHandler canvasHandler) {
        super(canvasHandler);
    }
    
}
