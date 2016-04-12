package org.wirez.core.client.view;

import org.wirez.core.client.view.event.ViewEvent;
import org.wirez.core.client.view.event.ViewEventType;
import org.wirez.core.client.view.event.ViewHandler;

public interface HasEventHandlers<T> {
    
    boolean supports( ViewEventType type );
    
    T addHandler(ViewEventType type, ViewHandler<? extends ViewEvent> eventHandler);
    
    T removeHandler(ViewHandler<? extends ViewEvent> eventHandler);
}
