package org.wirez.core.client.shape.view.event;

public interface ViewHandler<E extends org.wirez.core.client.shape.view.event.ViewEvent> {
    
    org.wirez.core.client.shape.view.event.ViewEventType getType();
    
    void handle(E event);
    
}
