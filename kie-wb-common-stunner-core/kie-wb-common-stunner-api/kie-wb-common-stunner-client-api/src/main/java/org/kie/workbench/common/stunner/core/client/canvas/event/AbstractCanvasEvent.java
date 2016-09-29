package org.kie.workbench.common.stunner.core.client.canvas.event;

import org.uberfire.workbench.events.UberFireEvent;
import org.kie.workbench.common.stunner.core.client.canvas.Canvas;

public abstract class AbstractCanvasEvent implements UberFireEvent {
    
    protected final Canvas canvas;

    public AbstractCanvasEvent(Canvas canvas) {
        this.canvas = canvas;
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
