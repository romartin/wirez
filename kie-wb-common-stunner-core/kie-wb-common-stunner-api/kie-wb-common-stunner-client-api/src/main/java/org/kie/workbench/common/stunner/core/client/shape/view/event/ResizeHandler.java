package org.kie.workbench.common.stunner.core.client.shape.view.event;

public abstract class ResizeHandler extends AbstractViewHandler<ResizeEvent> {

    @Override
    public ViewEventType getType() {
        return ViewEventType.RESIZE;
    }

    public abstract void start( ResizeEvent event );

    public abstract void end( ResizeEvent event );
}
