package org.wirez.client.widgets.session.impl;

import com.google.gwt.logging.client.LogConfiguration;
import org.uberfire.client.workbench.widgets.common.ErrorPopupPresenter;
import org.uberfire.mvp.Command;
import org.wirez.client.widgets.session.ReadOnlySessionPresenter;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.service.ClientDiagramServices;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.service.ServiceCallback;
import org.wirez.core.client.session.CanvasReadOnlySession;
import org.wirez.core.client.session.CanvasSessionManager;
import org.wirez.core.client.session.impl.DefaultCanvasSessionManager;

import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractReadOnlySessionPresenter<S extends CanvasReadOnlySession<AbstractCanvas, AbstractCanvasHandler>> 
        extends AbstractCanvasSessionPresenter<S> 
        implements ReadOnlySessionPresenter<AbstractCanvas, AbstractCanvasHandler, S> {

    private static Logger LOGGER = Logger.getLogger(AbstractReadOnlySessionPresenter.class.getName());
    
    DefaultCanvasSessionManager canvasSessionManager;
    ClientDiagramServices clientDiagramServices;

    @Inject
    public AbstractReadOnlySessionPresenter(final DefaultCanvasSessionManager canvasSessionManager,
                                            final ClientDiagramServices clientDiagramServices,
                                            final ErrorPopupPresenter errorPopupPresenter) {
        super( errorPopupPresenter );
        this.canvasSessionManager = canvasSessionManager;
        this.clientDiagramServices = clientDiagramServices;
    }

    @Override
    public void initialize(S session, int width, int height) {
        super.initialize(session, width, height);

        // Enable canvas controls.
        session.getShapeSelectionControl().enable( session.getCanvas() );
        
    }

    @Override
    public void load(final String diagramUUID, 
                     final Command callback) {

        clientDiagramServices.get( diagramUUID, new ServiceCallback<Diagram>() {
            @Override
            public void onSuccess(final Diagram diagram) {
                open(diagram, callback);
            }

            @Override
            public void onError(final ClientRuntimeError error) {
                showError( error );
                callback.execute();
            }
        } );
        
    }

    protected void open(final Diagram diagram,
                     final Command callback) {

        // Draw the graph on the canvas.
        getCanvasHandler().draw( diagram );
        canvasSessionManager.open( session );
        callback.execute();

    }

    @Override
    public void clearSelection() {
        session.getShapeSelectionControl().clearSelection();
    }

    @Override
    protected void showError(String message) {
        log( Level.SEVERE, message);
        super.showError(message);
    }

    private void log(final Level level, final String message) {
        if (LogConfiguration.loggingIsEnabled()) {
            LOGGER.log(level, message);
        }
    }
    
}
