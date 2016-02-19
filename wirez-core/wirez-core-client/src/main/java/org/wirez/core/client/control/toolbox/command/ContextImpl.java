package org.wirez.core.client.control.toolbox.command;

import org.wirez.core.api.command.CommandManager;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.command.CanvasCommandViolation;
import org.wirez.core.client.canvas.command.WiresCanvasCommandManager;
import org.wirez.core.client.canvas.impl.WiresCanvasHandler;

public class ContextImpl implements Context {
    
    private final WiresCanvasHandler canvasHandler;
    private final double x;
    private final double y;

    public ContextImpl(final WiresCanvasHandler canvasHandler, 
                       final double x, 
                       final double y) {
        this.canvasHandler = canvasHandler;
        this.x = x;
        this.y = y;
    }

    @Override
    public WiresCanvasHandler getCanvasHandler() {
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
