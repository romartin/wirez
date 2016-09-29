package org.kie.workbench.common.stunner.client.widgets.session.presenter;

import org.uberfire.mvp.Command;
import org.kie.workbench.common.stunner.core.client.canvas.Canvas;
import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;
import org.kie.workbench.common.stunner.core.client.session.CanvasFullSession;

public interface FullSessionPresenter<C extends Canvas, H extends CanvasHandler, S extends CanvasFullSession<C, H>>
        extends ReadOnlySessionPresenter<C, H, S> {

    void newDiagram(String uuid, String title, String definitionSetId, String shapeSetId, Command callback );

    void save( Command callback );

    void clear();

    void undo();

    void deleteSelected();

}
