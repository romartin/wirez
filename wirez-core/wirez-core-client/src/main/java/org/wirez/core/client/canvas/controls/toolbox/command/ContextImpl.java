package org.wirez.core.client.canvas.controls.toolbox.command;

import org.wirez.core.client.canvas.AbstractCanvasHandler;

public class ContextImpl implements Context {
    
    private final AbstractCanvasHandler canvasHandler;
    private final Event event;
    private final double x;
    private final double y;

    public ContextImpl(final AbstractCanvasHandler canvasHandler,
                       final Event event, 
                       final double x,
                       final double y) {
        this.canvasHandler = canvasHandler;
        this.event = event;
        this.x = x;
        this.y = y;
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
}
