package org.wirez.core.client.shape.view;

import com.ait.lienzo.client.core.shape.Shape;
import org.wirez.core.client.shape.view.event.ViewEvent;
import org.wirez.core.client.shape.view.event.ViewEventType;
import org.wirez.core.client.shape.view.event.ViewHandler;

public interface HasEventHandlers<T> {
    
    boolean supports( ViewEventType type );
    
    T addHandler(ViewEventType type, ViewHandler<? extends ViewEvent> eventHandler);
    
    T removeHandler(ViewHandler<? extends ViewEvent> eventHandler);
    
    Shape<?> getAttachableShape();
    
}
