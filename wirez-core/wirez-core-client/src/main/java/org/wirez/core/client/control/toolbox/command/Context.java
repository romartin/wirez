package org.wirez.core.client.control.toolbox.command;

import org.wirez.core.api.command.CommandManager;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.command.CanvasCommandViolation;
import org.wirez.core.client.canvas.command.WiresCanvasCommandManager;
import org.wirez.core.client.canvas.impl.WiresCanvasHandler;

public interface Context {

    WiresCanvasHandler getCanvasHandler();

    double getX();

    double getY();
}
