package org.wirez.core.client.canvas.event.selection;

import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.event.AbstractCanvasHandlerEvent;

public final class CanvasClearSelectionEvent extends AbstractCanvasHandlerEvent<CanvasHandler> {

    public CanvasClearSelectionEvent( final CanvasHandler canvasHandler ) {
        super( canvasHandler );
    }

}
