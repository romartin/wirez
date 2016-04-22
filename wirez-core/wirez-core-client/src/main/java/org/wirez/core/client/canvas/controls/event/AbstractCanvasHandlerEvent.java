package org.wirez.core.client.canvas.controls.event;

import org.uberfire.workbench.events.UberFireEvent;
import org.wirez.core.client.canvas.CanvasHandler;

public abstract class AbstractCanvasHandlerEvent<H extends CanvasHandler> implements UberFireEvent {
    
    protected final H canvasHandler;

    public AbstractCanvasHandlerEvent(final H canvasHandler) {
        this.canvasHandler = canvasHandler;
    }

    public H getCanvasHandler() {
        return canvasHandler;
    }
    
}
