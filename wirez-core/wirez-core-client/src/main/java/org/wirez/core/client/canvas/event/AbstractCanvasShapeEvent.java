package org.wirez.core.client.canvas.event;

import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.shape.Shape;

public abstract class AbstractCanvasShapeEvent extends AbstractCanvasEvent {
    
    protected final Shape shape;

    public AbstractCanvasShapeEvent(final Canvas canvas, 
                                    final Shape shape) {
        super(canvas);
        this.shape = shape;
    }

    public Shape getShape() {
        return shape;
    }
    
}
