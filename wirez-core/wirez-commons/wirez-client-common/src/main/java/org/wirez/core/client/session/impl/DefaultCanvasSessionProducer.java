package org.wirez.core.client.session.impl;

import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.session.CanvasSessionProducer;

public interface DefaultCanvasSessionProducer extends CanvasSessionProducer<AbstractCanvas, AbstractCanvasHandler> {

    DefaultCanvasReadOnlySession newReadOnlySession();

    DefaultCanvasFullSession newFullSession();
    
}
