package org.kie.workbench.common.stunner.core.client.canvas.event.registration;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.kie.workbench.common.stunner.core.client.canvas.Canvas;
import org.kie.workbench.common.stunner.core.client.shape.Shape;

@NonPortable
public final class CanvasShapeRemovedEvent extends AbstractCanvasShapeEvent {

    public CanvasShapeRemovedEvent(final Canvas canvas,
                                   final Shape shape) {
        super(canvas, shape);
    }
    
}
