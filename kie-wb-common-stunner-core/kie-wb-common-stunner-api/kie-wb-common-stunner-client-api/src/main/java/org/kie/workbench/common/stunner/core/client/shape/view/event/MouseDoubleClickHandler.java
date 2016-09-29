package org.kie.workbench.common.stunner.core.client.shape.view.event;

public abstract class MouseDoubleClickHandler extends AbstractViewHandler<MouseDoubleClickEvent> {

    @Override
    public ViewEventType getType() {
        return ViewEventType.MOUSE_DBL_CLICK;
    }

   
    
}
