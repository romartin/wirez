package org.wirez.core.client.canvas.controls.toolbox.command;

import org.wirez.core.client.canvas.CanvasHandler;

public interface Context<H extends CanvasHandler> {

    enum Event {
        CLICK, MOUSE_ENTER, MOUSE_EXIT, MOUSE_DOWN;
    }
    
    H getCanvasHandler();

    Event getEvent();
    
    double getX();

    double getY();

    double getClientX();

    double getClientY();
}
