package org.wirez.core.client.view.event;

public abstract class MouseMoveHandler extends AbstractViewHandler<MouseMoveEvent> {

    @Override
    public ViewEventType getType() {
        return ViewEventType.MOUSE_MOVE;
    }

}
