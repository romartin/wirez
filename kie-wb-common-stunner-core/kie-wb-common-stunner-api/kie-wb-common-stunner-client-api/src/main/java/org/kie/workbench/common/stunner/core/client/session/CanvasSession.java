package org.kie.workbench.common.stunner.core.client.session;

import org.kie.workbench.common.stunner.core.client.canvas.Canvas;
import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;

public interface CanvasSession<C extends Canvas, H extends CanvasHandler> {
    
    void onOpen();
    
    void onDispose();
    
    C getCanvas();
    
    H getCanvasHandler();
    
}
