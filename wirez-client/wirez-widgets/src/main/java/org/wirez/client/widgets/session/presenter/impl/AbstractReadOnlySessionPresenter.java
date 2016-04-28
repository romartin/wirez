package org.wirez.client.widgets.session.presenter.impl;

import com.google.gwt.logging.client.LogConfiguration;
import org.uberfire.client.workbench.widgets.common.ErrorPopupPresenter;
import org.uberfire.mvp.Command;
import org.wirez.client.widgets.session.presenter.ReadOnlySessionPresenter;
import org.wirez.client.widgets.session.toolbar.AbstractToolbar;
import org.wirez.client.widgets.session.toolbar.ToolbarCommand;
import org.wirez.client.widgets.session.toolbar.ToolbarCommandCallback;
import org.wirez.client.widgets.session.toolbar.command.ClearSelectionCommand;
import org.wirez.client.widgets.session.toolbar.command.VisitGraphCommand;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.event.CanvasProcessingCompletedEvent;
import org.wirez.core.client.canvas.event.CanvasProcessingStartedEvent;
import org.wirez.core.client.service.ClientDiagramServices;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.service.ServiceCallback;
import org.wirez.core.client.session.CanvasReadOnlySession;
import org.wirez.core.client.session.impl.DefaultCanvasReadOnlySession;
import org.wirez.core.client.session.impl.DefaultCanvasSessionManager;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractReadOnlySessionPresenter<S extends CanvasReadOnlySession<AbstractCanvas, AbstractCanvasHandler>> 
        extends AbstractCanvasSessionPresenter<S> 
        implements ReadOnlySessionPresenter<AbstractCanvas, AbstractCanvasHandler, S> {

    private static Logger LOGGER = Logger.getLogger(AbstractReadOnlySessionPresenter.class.getName());
    
    protected DefaultCanvasSessionManager canvasSessionManager;
    protected ClientDiagramServices clientDiagramServices;
    protected AbstractToolbar<S> toolbar;
    protected ClearSelectionCommand clearSelectionCommand;
    protected VisitGraphCommand visitGraphCommand;
    protected Event<CanvasProcessingStartedEvent> canvasProcessingStartedEvent;
    protected Event<CanvasProcessingCompletedEvent> canvasProcessingCompletedEvent;

    @Inject
    public AbstractReadOnlySessionPresenter(final DefaultCanvasSessionManager canvasSessionManager,
                                            final ClientDiagramServices clientDiagramServices,
                                            final AbstractToolbar<S> toolbar,
                                            final ClearSelectionCommand clearSelectionCommand,
                                            final VisitGraphCommand visitGraphCommand,
                                            final ErrorPopupPresenter errorPopupPresenter,
                                            final Event<CanvasProcessingStartedEvent> canvasProcessingStartedEvent,
                                            final Event<CanvasProcessingCompletedEvent> canvasProcessingCompletedEvent,
                                            final View view) {
        super( errorPopupPresenter, view );
        this.canvasSessionManager = canvasSessionManager;
        this.clientDiagramServices = clientDiagramServices;
        this.toolbar = toolbar;
        this.clearSelectionCommand = clearSelectionCommand;
        this.visitGraphCommand = visitGraphCommand;
        this.canvasProcessingStartedEvent = canvasProcessingStartedEvent;
        this.canvasProcessingCompletedEvent = canvasProcessingCompletedEvent;
    }

    @Override
    public void doInitialize(S session, int width, int height) {
        super.doInitialize(session, width, height);

        // Enable canvas controls.
        session.getShapeSelectionControl().enable( session.getCanvas() );
        session.getZoomControl().enable( session.getCanvas() );
        session.getPanControl().enable( session.getCanvas() );

        // Toolbar read-only commands.
        setToolbarCommands();
        
    }
    
    protected void setToolbarCommands() {
        addToolbarCommand( (ToolbarCommand<S>) this.clearSelectionCommand );
        addToolbarCommand( (ToolbarCommand<S>) this.visitGraphCommand );
    }

    @Override
    public void showToolbar() {
        toolbar.show();
    }

    @Override
    public void hideToolbar() {
        toolbar.hide();
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        view.setToolbar( toolbar.asWidget() );
    }

    @Override
    protected void afterInitialize(S session, int width, int height) {
        super.afterInitialize(session, width, height);
        
        toolbar.configure( session, new ToolbarCommandCallback<Object>() {
            @Override
            public void onSuccess(final Object result) {
                
            }

            @Override
            public void onError(final ClientRuntimeError error) {
                showError( error );
            }
        });
    }

    protected void addToolbarCommand(final ToolbarCommand<S> command ) {
        toolbar.addCommand( command );
    }

    @Override
    public void load(final String diagramUUID, 
                     final Command callback) {

        // Notify processing starts.
        fireProcessingStarted();
                
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
       
        // Notify processing ends.
        fireProcessingCompleted();

        callback.execute();

    }

    @Override
    public void clearSelection() {
        clearSelectionCommand.execute( new ToolbarCommandCallback<Void>() {
            @Override
            public void onSuccess(final Void result) {
                
            }

            @Override
            public void onError(final ClientRuntimeError error) {
                showError( error );
            }
        });
    }

    protected void fireProcessingStarted() {
        canvasProcessingStartedEvent.fire( new CanvasProcessingStartedEvent( getCanvasHandler() ) );
    }

    protected void fireProcessingCompleted() {
        canvasProcessingCompletedEvent.fire( new CanvasProcessingCompletedEvent( getCanvasHandler() ) );
    }
    
    @Override
    protected void showError(String message) {
        fireProcessingCompleted();
        log( Level.SEVERE, message);
        super.showError(message);
    }

    private void log(final Level level, final String message) {
        if (LogConfiguration.loggingIsEnabled()) {
            LOGGER.log(level, message);
        }
    }
    
}
