package org.kie.workbench.common.stunner.client.widgets.session.presenter;

import com.google.gwt.user.client.ui.IsWidget;
import org.kie.workbench.common.stunner.core.client.canvas.Canvas;
import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;
import org.kie.workbench.common.stunner.core.client.session.CanvasSession;

public interface CanvasSessionPresenter<C extends Canvas, H extends CanvasHandler, S extends CanvasSession<C, H>>
        extends IsWidget {

    interface View extends IsWidget {

        View setToolbar( IsWidget widget );

        View setCanvas( IsWidget widget );

        View setLoading( boolean loading );

        void destroy();

    }

    void initialize( S session, int width, int height );

    H getCanvasHandler();

}
