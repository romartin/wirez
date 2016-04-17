package org.wirez.core.client.canvas.event;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.CanvasHandler;

@NonPortable
public final class CanvasClearEvent extends AbstractCanvasEvent {

    public CanvasClearEvent(final Canvas canvas) {
        super(canvas);
    }
    
}
