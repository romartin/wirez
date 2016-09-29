package org.kie.workbench.common.stunner.core.client.canvas.event.selection;

import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;
import org.kie.workbench.common.stunner.core.client.canvas.event.AbstractCanvasHandlerEvent;

public final class CanvasClearSelectionEvent extends AbstractCanvasHandlerEvent<CanvasHandler> {

    public CanvasClearSelectionEvent( final CanvasHandler canvasHandler ) {
        super( canvasHandler );
    }

}
