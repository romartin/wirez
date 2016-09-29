package org.kie.workbench.common.stunner.core.client.shape.view.event;

public abstract class DragHandler extends AbstractViewHandler<DragEvent> {

    @Override
    public ViewEventType getType() {
        return ViewEventType.DRAG;
    }

    public abstract void start(DragEvent event);

    public abstract void end(DragEvent event);
}
