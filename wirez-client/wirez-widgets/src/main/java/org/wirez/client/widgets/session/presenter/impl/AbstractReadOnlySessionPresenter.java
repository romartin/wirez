package org.wirez.client.widgets.session.presenter.impl;

import com.google.gwt.logging.client.LogConfiguration;
import org.uberfire.client.workbench.widgets.common.ErrorPopupPresenter;
import org.uberfire.mvp.Command;
import org.wirez.client.widgets.session.presenter.ReadOnlySessionPresenter;
import org.wirez.client.widgets.session.toolbar.AbstractToolbar;
import org.wirez.client.widgets.session.toolbar.ToolbarCommand;
import org.wirez.client.widgets.session.toolbar.ToolbarCommandCallback;
import org.wirez.client.widgets.session.toolbar.command.ClearSelectionCommand;
import org.wirez.client.widgets.session.toolbar.command.SwitchGridCommand;
import org.wirez.client.widgets.session.toolbar.command.VisitGraphCommand;
import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.event.processing.CanvasProcessingCompletedEvent;
import org.wirez.core.client.canvas.event.processing.CanvasProcessingStartedEvent;
import org.wirez.core.client.canvas.listener.CanvasElementListener;
import org.wirez.core.client.canvas.listener.CanvasShapeListener;
import org.wirez.core.client.service.ClientDiagramServices;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.service.ServiceCallback;
import org.wirez.core.client.session.CanvasReadOnlySession;
import org.wirez.core.client.session.impl.DefaultCanvasSessionManager;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.diagram.Diagram;
import org.wirez.core.graph.Element;

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
    protected SwitchGridCommand switchGridCommand;
    protected Event<CanvasProcessingStartedEvent> canvasProcessingStartedEvent;
    protected Event<CanvasProcessingCompletedEvent> canvasProcessingCompletedEvent;

    @Inject
    public AbstractReadOnlySessionPresenter(final DefaultCanvasSessionManager canvasSessionManager,
                                            final ClientDiagramServices clientDiagramServices,
                                            final AbstractToolbar<S> toolbar,
                                            final ClearSelectionCommand clearSelectionCommand,
                                            final VisitGraphCommand visitGraphCommand,
                                            final SwitchGridCommand switchGridCommand,
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
        this.switchGridCommand = switchGridCommand;
        this.canvasProcessingStartedEvent = canvasProcessingStartedEvent;
        this.canvasProcessingCompletedEvent = canvasProcessingCompletedEvent;
    }

    @Override
    public void doInitialize(S session, int width, int height) {
        super.doInitialize(session, width, height);

        final AbstractCanvas canvas = getCanvasHandler().getCanvas();
        canvas.addRegistrationListener( new CanvasShapeListener() {

            @Override
            public void register( final Shape item ) {

                onRegisterShape( item );

            }

            @Override
            public void deregister( final Shape item ) {

                onDeregisterShape( item );

            }

            @Override
            public void clear() {


                onClear();

            }

        } );

        getCanvasHandler().addRegistrationListener( new CanvasElementListener() {

            @Override
            public void update( final Element item ) {

                onUpdateElement( item );

            }

            @Override
            public void register( final Element item ) {

                onRegisterElement( item );

            }

            @Override
            public void deregister( final Element item ) {

                onDeregisterElement( item );

            }

            @Override
            public void clear() {

                onClear();

            }

        } );

        // Enable canvas controls.
        enableControl( session.getShapeSelectionControl(), session.getCanvasHandler() );
        enableControl( session.getZoomControl(), session.getCanvas() );
        enableControl( session.getPanControl(), session.getCanvas() );
       
        // Toolbar read-only commands.
        setToolbarCommands();
        
    }

    protected void onClear() {

        fireRegistrationClearListeners( session.getShapeSelectionControl() );

    }

    protected void onRegisterShape( final Shape shape ) {

        onShapeRegistration( shape, true );

    }

    protected void onDeregisterShape( final Shape shape ) {

        onShapeRegistration( shape, false );

    }

    protected void onShapeRegistration( final Shape shape,
                                        final boolean add ) {

        fireRegistrationListeners( session.getZoomControl(), shape, add );
        fireRegistrationListeners( session.getPanControl(), shape, add );

    }

    protected void onRegisterElement( final Element element ) {

        onElementRegistration( element, true );

    }

    protected void onDeregisterElement( final Element element ) {

        onElementRegistration( element, false );

    }

    protected void onElementRegistration( final Element element,
                                          final boolean add ) {

        fireRegistrationListeners( session.getShapeSelectionControl(), element, add );

    }

    protected void onUpdateElement( final Element element ) {

        fireRegistrationUpdateListeners( session.getShapeSelectionControl(), element );

    }
    
    protected void setToolbarCommands() {
        addToolbarCommand( (ToolbarCommand<S>) this.clearSelectionCommand );
        addToolbarCommand( (ToolbarCommand<S>) this.visitGraphCommand );
        addToolbarCommand( (ToolbarCommand<S>) this.switchGridCommand );
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

    @Override
    protected void disposeSession() {
        
        if ( null != toolbar ) {
            this.toolbar.destroy();
        }
        
        if ( null != clearSelectionCommand ) {
            this.clearSelectionCommand.destroy();
        }
        
        if ( null != visitGraphCommand ) {
            this.visitGraphCommand.destroy();
        }

        if ( null != switchGridCommand ) {
            this.switchGridCommand.destroy();
        }
        
        this.canvasSessionManager = null;
        this.clientDiagramServices = null;
        this.toolbar = null;
        this.clearSelectionCommand = null;
        this.visitGraphCommand = null;
        this.switchGridCommand = null;

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
