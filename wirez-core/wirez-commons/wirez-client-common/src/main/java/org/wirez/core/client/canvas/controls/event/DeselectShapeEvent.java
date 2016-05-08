package org.wirez.core.client.canvas.controls.event;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.shape.Shape;

/**
 * Event for requesting the selection control for the given canvas to de-select a shape. 
 */
@NonPortable
public final class DeselectShapeEvent extends AbstractShapeSelectionControlEvent<AbstractCanvas> {
    
    public DeselectShapeEvent(final AbstractCanvas canvas,
                              final Shape shape) {
        super(canvas, shape);
    }
    
}
