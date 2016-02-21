package org.wirez.core.client.control.toolbox.command;

import org.wirez.core.client.canvas.wires.WiresCanvasHandler;

public interface Context {

    WiresCanvasHandler getCanvasHandler();

    double getX();

    double getY();
}
