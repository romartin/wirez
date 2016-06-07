package org.wirez.core.client.canvas.controls.toolbox.command;

import org.wirez.core.client.canvas.AbstractCanvasHandler;

public interface Context {

    AbstractCanvasHandler getCanvasHandler();

    double getX();

    double getY();
}