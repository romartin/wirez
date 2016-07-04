package org.wirez.core.client.canvas.event.registration;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.event.AbstractCanvasHandlerEvent;

@NonPortable
public final class CanvasElementsClearEvent extends AbstractCanvasHandlerEvent<CanvasHandler> {

    public CanvasElementsClearEvent( final CanvasHandler canvasHandler ) {
        super( canvasHandler );
    }

}
