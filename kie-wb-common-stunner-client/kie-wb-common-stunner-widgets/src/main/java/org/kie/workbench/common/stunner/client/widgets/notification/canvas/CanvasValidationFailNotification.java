package org.kie.workbench.common.stunner.client.widgets.notification.canvas;

import org.kie.workbench.common.stunner.client.widgets.notification.AbstractNotification;
import org.kie.workbench.common.stunner.core.validation.ValidationViolation;

public final class CanvasValidationFailNotification extends AbstractNotification<Iterable<ValidationViolation>, CanvasNotificationContext> {

    @SuppressWarnings( "unchecked" )
    public CanvasValidationFailNotification( final String uuid,
                                             final Iterable<? extends ValidationViolation> source,
                                             final CanvasNotificationContext context ) {
        super( uuid, Type.ERROR, ( Iterable<ValidationViolation> ) source, context );
    }

}
