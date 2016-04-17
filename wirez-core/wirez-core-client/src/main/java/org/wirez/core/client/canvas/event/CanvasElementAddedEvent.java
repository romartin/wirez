package org.wirez.core.client.canvas.event;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.wirez.core.api.graph.Element;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.shape.Shape;

@NonPortable
public final class CanvasElementAddedEvent extends AbstractCanvasHandlerElementEvent {

    public CanvasElementAddedEvent(final CanvasHandler canvasHandler, 
                                   final Element<?> element) {
        super(canvasHandler, element);
    }
    
}
