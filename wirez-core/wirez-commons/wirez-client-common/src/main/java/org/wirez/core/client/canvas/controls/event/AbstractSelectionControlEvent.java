package org.wirez.core.client.canvas.controls.event;

import org.uberfire.workbench.events.UberFireEvent;
import org.wirez.core.client.canvas.Canvas;

public abstract class AbstractSelectionControlEvent<C extends Canvas> implements UberFireEvent {
    
    protected final C canvas;

    public AbstractSelectionControlEvent(final C canvas) {
        this.canvas = canvas;
    }

    public C getCanvas() {
        return canvas;
    }

}
