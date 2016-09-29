package org.kie.workbench.common.stunner.client.widgets.session.presenter;

import org.uberfire.mvp.Command;
import org.kie.workbench.common.stunner.core.client.canvas.Canvas;
import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;
import org.kie.workbench.common.stunner.core.client.session.CanvasReadOnlySession;

public interface ReadOnlySessionPresenter<C extends Canvas, H extends CanvasHandler, S extends CanvasReadOnlySession<C, H>>
        extends CanvasSessionPresenter<C, H, S> {

    void load( String diagramUUID, Command callback );
    
    void clearSelection();

    void showToolbar();

    void hideToolbar();
    
}
