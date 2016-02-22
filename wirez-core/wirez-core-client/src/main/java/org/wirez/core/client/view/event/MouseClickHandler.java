package org.wirez.core.client.view.event;

public abstract class MouseClickHandler extends AbstractViewHandler<MouseClickEvent> {

    @Override
    public ViewEventType getType() {
        return ViewEventType.MOUSE_CLICK;
    }

   
    
}
