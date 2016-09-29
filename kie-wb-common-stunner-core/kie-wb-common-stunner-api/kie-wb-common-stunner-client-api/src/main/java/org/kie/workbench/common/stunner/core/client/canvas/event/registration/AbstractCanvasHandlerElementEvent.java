package org.kie.workbench.common.stunner.core.client.canvas.event.registration;

import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;
import org.kie.workbench.common.stunner.core.client.canvas.event.AbstractCanvasHandlerEvent;
import org.kie.workbench.common.stunner.core.graph.Element;

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
