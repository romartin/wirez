package org.wirez.core.validation.canvas;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.validation.event.AbstractValidationEvent;

@NonPortable
public class CanvasValidationSuccessEvent extends AbstractValidationEvent<CanvasHandler> {

    public CanvasValidationSuccessEvent( final CanvasHandler entity ) {
        super( entity );
    }

}
