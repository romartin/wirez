package org.wirez.client.widgets.session.presenter.impl;

import com.google.gwt.user.client.ui.Widget;
import org.uberfire.client.workbench.widgets.common.ErrorPopupPresenter;
import org.wirez.client.widgets.session.presenter.CanvasSessionPresenter;
import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.session.CanvasSession;

public abstract class AbstractCanvasSessionPresenter<S extends CanvasSession<AbstractCanvas, AbstractCanvasHandler>>
        implements CanvasSessionPresenter<AbstractCanvas, AbstractCanvasHandler, S> {

    ErrorPopupPresenter errorPopupPresenter;
    View view;
    protected S session;

    public AbstractCanvasSessionPresenter(final ErrorPopupPresenter errorPopupPresenter,
                                          final View view) {
        this.errorPopupPresenter = errorPopupPresenter;
        this.view = view;
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

        // Initialize the view.
        initializeView();
        
        doInitialize( session, width, height );

        afterInitialize( session, width, height );


    }

    protected void doInitialize(final S session,
                           final int width,
                           final int height) {
        
    }

    protected void afterInitialize(final S session,
                                   final int width,
                                   final int height) {

    }
    
    protected void initializeView() {

        view.setCanvas( session.getCanvas().getView().asWidget() );
        
    }

    @Override
    public AbstractCanvasHandler getCanvasHandler() {
        return session.getCanvasHandler();
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
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
