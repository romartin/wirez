package org.kie.workbench.common.stunner.core.client.canvas.controls.toolbox.command;

import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;

public class ContextImpl implements Context {
    
    private final AbstractCanvasHandler canvasHandler;
    private final Event event;
    private final double x;
    private final double y;
    private final double clientX;
    private final double clientY;

    public ContextImpl(final AbstractCanvasHandler canvasHandler,
                       final Event event,
                       final double x,
                       final double y,
                       final double clientX,
                       final double clientY) {
        this.canvasHandler = canvasHandler;
        this.event = event;
        this.x = x;
        this.y = y;
        this.clientX = clientX;
        this.clientY = clientY;
    }

    @Override
    public AbstractCanvasHandler getCanvasHandler() {
        return canvasHandler;
    }

    @Override
    public Event getEvent() {
        return event;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getClientX() {
        return clientX;
    }

    @Override
    public double getClientY() {
        return clientY;
    }

}
