package org.kie.workbench.common.stunner.core.client.shape.view;

import org.kie.workbench.common.stunner.core.client.shape.view.event.ViewEvent;
import org.kie.workbench.common.stunner.core.client.shape.view.event.ViewEventType;
import org.kie.workbench.common.stunner.core.client.shape.view.event.ViewHandler;

public interface HasEventHandlers<T, S> {
    
    boolean supports(ViewEventType type);
    
    T addHandler(ViewEventType type, ViewHandler<? extends ViewEvent> eventHandler);
    
    T removeHandler(ViewHandler<? extends ViewEvent> eventHandler);

    T enableHandlers();

    T disableHandlers();

    S getAttachableShape();
    
}
