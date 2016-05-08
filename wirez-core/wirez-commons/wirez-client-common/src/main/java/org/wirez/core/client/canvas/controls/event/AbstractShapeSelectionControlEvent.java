package org.wirez.core.client.canvas.controls.event;

import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.shape.Shape;

public abstract class AbstractShapeSelectionControlEvent<C extends Canvas> extends AbstractSelectionControlEvent<C> {
    
    protected final Shape shape;

    public AbstractShapeSelectionControlEvent(final C canvas,
                                              final Shape shape) {
        super( canvas );
        this.shape = shape;
    }

    public Shape getShape() {
        return shape;
    }
    
}
