package org.kie.workbench.common.stunner.core.client.canvas.event;

import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;
import org.uberfire.workbench.events.UberFireEvent;

public abstract class AbstractCanvasHandlerEvent<H extends CanvasHandler> implements UberFireEvent {

    protected final H canvasHandler;

    public AbstractCanvasHandlerEvent(final H canvasHandler) {
        this.canvasHandler = canvasHandler;
    }

    public H getCanvasHandler() {
        return canvasHandler;
    }
}
