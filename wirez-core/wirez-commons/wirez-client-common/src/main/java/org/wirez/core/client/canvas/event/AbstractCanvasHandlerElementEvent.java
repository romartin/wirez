package org.wirez.core.client.canvas.event;

import org.wirez.core.api.graph.Element;
import org.wirez.core.client.canvas.CanvasHandler;

public abstract class AbstractCanvasHandlerElementEvent extends AbstractCanvasHandlerEvent {

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
