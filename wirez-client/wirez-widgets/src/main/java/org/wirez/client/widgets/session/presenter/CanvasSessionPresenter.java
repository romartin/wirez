package org.wirez.client.widgets.session.presenter;

import com.google.gwt.user.client.ui.IsWidget;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.session.CanvasSession;

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
