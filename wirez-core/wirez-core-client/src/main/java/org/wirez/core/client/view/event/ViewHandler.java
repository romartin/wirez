package org.wirez.core.client.view.event;

public interface ViewHandler<E extends ViewEvent> {
    
    ViewEventType getType();
    
    void handle(E event);
    
}
