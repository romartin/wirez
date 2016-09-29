package org.kie.workbench.common.stunner.core.client.session.impl;

import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvas;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.client.session.CanvasSessionProducer;

public interface DefaultCanvasSessionProducer extends CanvasSessionProducer<AbstractCanvas, AbstractCanvasHandler> {

    DefaultCanvasReadOnlySession newReadOnlySession();

    DefaultCanvasFullSession newFullSession();
    
}
