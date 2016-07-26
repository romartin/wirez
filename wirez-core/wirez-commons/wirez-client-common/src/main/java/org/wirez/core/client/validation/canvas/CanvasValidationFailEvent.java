package org.wirez.core.client.validation.canvas;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.validation.canvas.CanvasValidationViolation;
import org.wirez.core.validation.event.AbstractValidationFailEvent;

@NonPortable
public class CanvasValidationFailEvent extends AbstractValidationFailEvent<CanvasHandler, CanvasValidationViolation> {

    public CanvasValidationFailEvent( final CanvasHandler entity,
                                      final Iterable<CanvasValidationViolation> violations ) {
        super( entity, violations );
    }

}
