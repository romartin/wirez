package org.kie.workbench.common.stunner.client.widgets.notification.canvas;

import org.kie.workbench.common.stunner.client.widgets.notification.AbstractNotification;

public final class CanvasValidationSuccessNotification extends AbstractNotification<String, CanvasNotificationContext> {

    public CanvasValidationSuccessNotification( final String uuid,
                                                final CanvasNotificationContext context ) {
        super( uuid, Type.INFO, "Validation successful", context );
    }

}
