package org.kie.workbench.common.stunner.core.client.canvas.event.registration;

import org.kie.workbench.common.stunner.core.client.canvas.event.AbstractCanvasEvent;
import org.kie.workbench.common.stunner.core.client.canvas.Canvas;
import org.kie.workbench.common.stunner.core.client.shape.Shape;

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
