package org.wirez.core.client.canvas.event;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.wirez.core.client.canvas.Canvas;

@NonPortable
public final class CanvasDrawnEvent extends AbstractCanvasEvent {

    public CanvasDrawnEvent(final Canvas canvas) {
        super(canvas);
    }
    
}
