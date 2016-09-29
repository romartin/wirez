package org.kie.workbench.common.stunner.core.client.canvas.event;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.kie.workbench.common.stunner.core.client.canvas.Canvas;

@NonPortable
public final class CanvasClearEvent extends AbstractCanvasEvent {

    public CanvasClearEvent(final Canvas canvas) {
        super(canvas);
    }
    
}
