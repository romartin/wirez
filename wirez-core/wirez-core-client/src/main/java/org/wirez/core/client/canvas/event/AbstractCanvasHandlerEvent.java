package org.wirez.core.client.canvas.event;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.uberfire.workbench.events.UberFireEvent;
import org.wirez.core.client.canvas.CanvasHandler;

public abstract class AbstractCanvasHandlerEvent implements UberFireEvent {

    protected final CanvasHandler canvasHandler;

    public AbstractCanvasHandlerEvent(final CanvasHandler canvasHandler) {
        this.canvasHandler = canvasHandler;
    }

    public CanvasHandler getCanvasHandler() {
        return canvasHandler;
    }
}
