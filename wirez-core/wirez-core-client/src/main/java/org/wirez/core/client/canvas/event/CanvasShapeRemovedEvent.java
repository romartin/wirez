package org.wirez.core.client.canvas.event;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.shape.Shape;

@NonPortable
public final class CanvasShapeRemovedEvent extends AbstractCanvasShapeEvent {

    public CanvasShapeRemovedEvent(final Canvas canvas,
                                   final Shape shape) {
        super(canvas, shape);
    }
    
}
