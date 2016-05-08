package org.wirez.core.client.canvas.controls.event;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.shape.Shape;

/**
 * Event for requesting the selection control for the given canvas to select a shape. 
 */
@NonPortable
public final class SelectShapeEvent extends AbstractShapeSelectionControlEvent<AbstractCanvas> {
    
    public SelectShapeEvent(final AbstractCanvas canvas,
                            final Shape shape) {
        super(canvas, shape);
    }
    
}
