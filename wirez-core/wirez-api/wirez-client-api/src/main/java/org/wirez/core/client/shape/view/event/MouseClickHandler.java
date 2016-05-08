package org.wirez.core.client.shape.view.event;

public abstract class MouseClickHandler extends org.wirez.core.client.shape.view.event.AbstractViewHandler<MouseClickEvent> {

    @Override
    public ViewEventType getType() {
        return ViewEventType.MOUSE_CLICK;
    }

   
    
}
