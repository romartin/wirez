package org.wirez.core.client.session;

import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.CanvasHandler;

public interface CanvasSessionProducer<C extends Canvas, H extends CanvasHandler>  {

    CanvasReadOnlySession<C, H> newReadOnlySession();
    
    CanvasFullSession<C, H> newFullSession();
    
}
