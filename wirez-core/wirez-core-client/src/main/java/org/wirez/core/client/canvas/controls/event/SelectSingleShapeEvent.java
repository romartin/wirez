package org.wirez.core.client.canvas.controls.event;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.shape.Shape;

/**
 * Event for requesting the selection control for the given canvas to first clear current selection and next select the given shape. 
 */
@NonPortable
public final class SelectSingleShapeEvent extends AbstractShapeSelectionControlEvent<AbstractCanvas> {
    
    public SelectSingleShapeEvent(final AbstractCanvas canvas,
                                  final Shape shape) {
        super(canvas, shape);
    }
    
}
