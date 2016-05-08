package org.wirez.core.client.session;

import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.CanvasHandler;

public interface CanvasSession<C extends Canvas, H extends CanvasHandler> {
    
    void onOpen();
    
    void onDispose();
    
    C getCanvas();
    
    H getCanvasHandler();
    
}
