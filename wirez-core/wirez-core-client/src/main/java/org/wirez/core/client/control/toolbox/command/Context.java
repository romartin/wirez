package org.wirez.core.client.control.toolbox.command;

import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.command.WiresCanvasCommandManager;

public interface Context {

    CanvasHandler getCanvasHandler();

    WiresCanvasCommandManager getCommandManager();
    
    double getX();

    double getY();
}
