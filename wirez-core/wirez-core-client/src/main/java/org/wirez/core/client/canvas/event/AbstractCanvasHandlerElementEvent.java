package org.wirez.core.client.canvas.event;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.wirez.core.api.graph.Element;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.shape.Shape;

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
