package org.wirez.core.client.shape.view.event;

public interface ViewHandler<E extends ViewEvent> {
    
    ViewEventType getType();
    
    void handle(E event);
    
}
