package org.wirez.core.client.shape.view.event;

public abstract class DragHandler extends org.wirez.core.client.shape.view.event.AbstractViewHandler<org.wirez.core.client.shape.view.event.DragEvent> {

    @Override
    public ViewEventType getType() {
        return ViewEventType.DRAG;
    }

    public abstract void start(org.wirez.core.client.shape.view.event.DragEvent event);

    public abstract void end(org.wirez.core.client.shape.view.event.DragEvent event);
}
