package org.kie.workbench.common.stunner.core.client.shape.view.event;

public abstract class MouseClickHandler extends AbstractViewHandler<MouseClickEvent> {

    @Override
    public ViewEventType getType() {
        return ViewEventType.MOUSE_CLICK;
    }

   
    
}
