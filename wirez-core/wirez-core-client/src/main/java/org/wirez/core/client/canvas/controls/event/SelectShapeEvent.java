package org.wirez.core.client.canvas.controls.event;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.shape.Shape;

@NonPortable
public final class SelectShapeEvent extends AbstractShapeSelectionControlEvent<AbstractCanvas> {
    
    public SelectShapeEvent(final AbstractCanvas canvas,
                            final Shape shape) {
        super(canvas, shape);
    }
    
}
