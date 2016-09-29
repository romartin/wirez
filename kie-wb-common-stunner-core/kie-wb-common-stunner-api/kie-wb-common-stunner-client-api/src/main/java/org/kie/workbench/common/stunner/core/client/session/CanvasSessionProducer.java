package org.kie.workbench.common.stunner.core.client.session;

import org.kie.workbench.common.stunner.core.client.canvas.Canvas;
import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;

public interface CanvasSessionProducer<C extends Canvas, H extends CanvasHandler>  {

    CanvasReadOnlySession<C, H> newReadOnlySession();
    
    CanvasFullSession<C, H> newFullSession();
    
}
