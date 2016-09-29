package org.kie.workbench.common.stunner.core.client.shape.view.event;

public abstract class TouchHandler extends AbstractViewHandler<TouchEvent> {

    @Override
    public ViewEventType getType() {
        return ViewEventType.TOUCH;
    }

    public abstract void start(TouchEvent event);

    public abstract void move(TouchEvent event);
    
    public abstract void end(TouchEvent event);

    public abstract void cancel(TouchEvent event);
    
}
