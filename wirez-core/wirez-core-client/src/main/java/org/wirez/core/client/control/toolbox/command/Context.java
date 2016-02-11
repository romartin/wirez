package org.wirez.core.client.control.toolbox.command;

import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.command.CanvasCommandManager;

public interface Context {

    CanvasHandler getCanvasHandler();

    CanvasCommandManager getCommandManager();
    
    double getX();

    double getY();
}
