package org.wirez.core.client.canvas.event.selection;

import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.event.AbstractCanvasHandlerEvent;

public final class CanvasElementSelectedEvent extends AbstractCanvasHandlerEvent<CanvasHandler> {

    private final String elementUUID;

    public CanvasElementSelectedEvent( final CanvasHandler canvasHandler,
                                       final String elementUUID ) {
        super( canvasHandler );
        this.elementUUID = elementUUID;
    }

    public String getElementUUID() {
        return elementUUID;
    }

}
