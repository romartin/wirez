package org.wirez.client.widgets.notification.canvas;

import org.wirez.client.widgets.notification.AbstractNotification;
import org.wirez.core.validation.ValidationViolation;

public final class CanvasValidationFailNotification extends AbstractNotification<Iterable<ValidationViolation>, CanvasNotificationContext> {

    @SuppressWarnings( "unchecked" )
    public CanvasValidationFailNotification( final String uuid,
                                             final Iterable<? extends ValidationViolation> source,
                                             final CanvasNotificationContext context ) {
        super( uuid, Type.ERROR, ( Iterable<ValidationViolation> ) source, context );
    }

}
