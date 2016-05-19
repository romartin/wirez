package org.wirez.core.client.canvas.event.registration;

import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.event.AbstractCanvasHandlerEvent;
import org.wirez.core.graph.Element;

public abstract class AbstractCanvasHandlerElementEvent extends AbstractCanvasHandlerEvent<CanvasHandler> {

    protected final Element<?> element;

    public AbstractCanvasHandlerElementEvent(final CanvasHandler canvasHandler,
                                             final Element<?> element) {
        super(canvasHandler);
        this.element = element;
    }

    public Element<?> getElement() {
        return element;
    }
    
}
