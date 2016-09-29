package org.kie.workbench.common.stunner.core.client.session;

import org.kie.workbench.common.stunner.core.client.canvas.Canvas;
import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;

public interface CanvasSessionManager<C extends Canvas, H extends CanvasHandler> 
        extends CanvasSessionProducer<C, H> {

    CanvasSession<C, H> getCurrentSession();
    
    void open(CanvasSession<C, H> session);

    void pause();

    void resume(CanvasSession<C, H> session);

    void dispose();
    
}
