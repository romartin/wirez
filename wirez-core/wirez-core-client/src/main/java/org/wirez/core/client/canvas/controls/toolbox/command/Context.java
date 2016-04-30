package org.wirez.core.client.canvas.controls.toolbox.command;

import org.wirez.core.client.canvas.AbstractCanvasHandler;

public interface Context {

    enum Event {
        CLICK, DRAG, MOUSE_ENTER, MOUSE_EXIT;     
    }
    
    AbstractCanvasHandler getCanvasHandler();

    Event getEvent();
    
    double getX();

    double getY();
}
