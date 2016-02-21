package org.wirez.core.client.view.event;

public class MouseClickHandler extends AbstractViewHandler<MouseClickEvent> {

    @Override
    public ViewEventType getType() {
        return ViewEventType.MOUSE_CLICK;
    }

    @Override
    public void handle(final MouseClickEvent event) {
        
    }
    
}
