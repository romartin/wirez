package org.kie.workbench.common.stunner.core.client.validation.canvas;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;
import org.kie.workbench.common.stunner.core.validation.event.AbstractValidationEvent;

@NonPortable
public class CanvasValidationSuccessEvent extends AbstractValidationEvent<CanvasHandler> {

    public CanvasValidationSuccessEvent( final CanvasHandler entity ) {
        super( entity );
    }

}
