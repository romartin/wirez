package org.wirez.core.client.session.impl;

import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.session.CanvasReadOnlySession;

public interface DefaultCanvasReadOnlySession extends CanvasReadOnlySession<AbstractCanvas, AbstractCanvasHandler>, DefaultCanvasSession{
}
