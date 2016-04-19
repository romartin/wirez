package org.wirez.client.widgets.session.impl;

import com.google.gwt.user.client.ui.Widget;
import org.uberfire.client.workbench.widgets.common.ErrorPopupPresenter;
import org.wirez.client.widgets.session.CanvasSessionPresenter;
import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.session.CanvasSession;

public abstract class AbstractCanvasSessionPresenter<S extends CanvasSession<AbstractCanvas, AbstractCanvasHandler>>
        implements CanvasSessionPresenter<AbstractCanvas, AbstractCanvasHandler, S> {

    ErrorPopupPresenter errorPopupPresenter;
    protected S session;

    public AbstractCanvasSessionPresenter(final ErrorPopupPresenter errorPopupPresenter) {
        this.errorPopupPresenter = errorPopupPresenter;
    }

    @Override
    public void initialize(final S session, 
                           final int width, 
                           final int height) {
        this.session = session;

        // Create the canvas with a given size.
        session.getCanvas().initialize( width, height );

        // Initialize the canvas to handle.
        getCanvasHandler().initialize( session.getCanvas() );
        
    }

    @Override
    public AbstractCanvasHandler getCanvasHandler() {
        return session.getCanvasHandler();
    }

    @Override
    public Widget asWidget() {
        return session.getCanvas().getView().asWidget();
    }

    protected void showError(final ClientRuntimeError error) {
        final String message = error.getCause() != null ? error.getCause() : error.getMessage();
        showError(message);
    }

    protected void showError(final Throwable throwable) {
        errorPopupPresenter.showMessage( throwable != null ? throwable.getMessage() : "Error");
    }

    protected void showError(final String message) {
        errorPopupPresenter.showMessage(message);
    }

}
