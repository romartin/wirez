package org.kie.workbench.common.stunner.core.client.canvas.event.registration;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;
import org.kie.workbench.common.stunner.core.graph.Element;

@NonPortable
public final class CanvasElementRemovedEvent extends AbstractCanvasHandlerElementEvent {

    public CanvasElementRemovedEvent(final CanvasHandler canvasHandler,
                                     final Element<?> element) {
        super(canvasHandler, element);
    }
    
}
