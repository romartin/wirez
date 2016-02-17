package org.wirez.core.client.control.toolbox.command;

import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.command.WiresCanvasCommandManager;

public class ContextImpl implements Context {
    
    private final CanvasHandler canvasHandler;
    private final WiresCanvasCommandManager commandManager;
    private final double x;
    private final double y;

    public ContextImpl(final CanvasHandler canvasHandler, 
                       final WiresCanvasCommandManager commandManager,
                       final double x, 
                       final double y) {
        this.canvasHandler = canvasHandler;
        this.commandManager = commandManager;
        this.x = x;
        this.y = y;
    }

    @Override
    public CanvasHandler getCanvasHandler() {
        return canvasHandler;
    }

    @Override
    public WiresCanvasCommandManager getCommandManager() {
        return commandManager;
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
