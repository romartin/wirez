package org.kie.workbench.common.stunner.core.client.shape.view.event;

public abstract class GestureHandler extends AbstractViewHandler<GestureEvent> {

    @Override
    public ViewEventType getType() {
        return ViewEventType.GESTURE;
    }

    public abstract void start(GestureEvent event);

    public abstract void change(GestureEvent event);
    
    public abstract void end(GestureEvent event);
    
}
