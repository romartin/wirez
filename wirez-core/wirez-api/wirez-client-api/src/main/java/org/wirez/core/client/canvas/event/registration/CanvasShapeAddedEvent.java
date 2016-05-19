package org.wirez.core.client.canvas.event.registration;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.shape.Shape;

@NonPortable
public final class CanvasShapeAddedEvent extends AbstractCanvasShapeEvent {

    public CanvasShapeAddedEvent(final Canvas canvas, 
                                 final Shape shape) {
        super(canvas, shape);
    }
    
}
