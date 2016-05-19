package org.wirez.core.client.canvas.event.processing;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.event.AbstractCanvasHandlerEvent;

@NonPortable
public final class CanvasInitializationCompletedEvent extends AbstractCanvasHandlerEvent<CanvasHandler> {

    public CanvasInitializationCompletedEvent(final CanvasHandler canvasHandler) {
        super(canvasHandler);
    }
    
}
