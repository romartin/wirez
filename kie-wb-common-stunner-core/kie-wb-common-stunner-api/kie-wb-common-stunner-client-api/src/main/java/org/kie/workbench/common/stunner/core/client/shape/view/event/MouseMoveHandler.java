package org.kie.workbench.common.stunner.core.client.shape.view.event;

public abstract class MouseMoveHandler extends AbstractViewHandler<MouseMoveEvent> {

    @Override
    public ViewEventType getType() {
        return ViewEventType.MOUSE_MOVE;
    }

    
}
