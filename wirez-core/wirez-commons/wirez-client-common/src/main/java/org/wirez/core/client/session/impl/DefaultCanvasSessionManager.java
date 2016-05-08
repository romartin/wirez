package org.wirez.core.client.session.impl;

import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.session.CanvasSessionManager;

public interface DefaultCanvasSessionManager extends CanvasSessionManager<AbstractCanvas, AbstractCanvasHandler> {

    @Override
    DefaultCanvasReadOnlySession newReadOnlySession();

    @Override
    DefaultCanvasFullSession newFullSession();
}
