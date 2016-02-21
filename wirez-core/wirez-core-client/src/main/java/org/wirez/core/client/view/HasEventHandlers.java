package org.wirez.core.client.view;

import org.wirez.core.client.view.event.ViewEvent;
import org.wirez.core.client.view.event.ViewHandler;
import org.wirez.core.client.view.event.ViewEventType;

public interface HasEventHandlers<T> {
    
    boolean supports( ViewEventType type );
    
    T addHandler(ViewEventType type, ViewHandler<ViewEvent> eventHandler);
    
    T removeHandler(ViewHandler<ViewEvent> eventHandler);
}
