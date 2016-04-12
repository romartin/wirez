package org.wirez.core.client.control.toolbox.command;

import org.wirez.core.client.canvas.AbstractCanvasHandler;

public class ContextImpl implements Context {
    
    private final AbstractCanvasHandler canvasHandler;
    private final double x;
    private final double y;

    public ContextImpl(final AbstractCanvasHandler canvasHandler, 
                       final double x, 
                       final double y) {
        this.canvasHandler = canvasHandler;
        this.x = x;
        this.y = y;
    }

    @Override
    public AbstractCanvasHandler getCanvasHandler() {
        return canvasHandler;
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
