package org.wirez.core.client.view.event;

public class ClickHandler extends AbstractViewHandler<ClickEvent> {

    @Override
    public ViewEventType getType() {
        return ViewEventType.CLICK;
    }

    @Override
    public void handle(final ClickEvent event) {
        
    }
    
}
