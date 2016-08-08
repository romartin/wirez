package org.wirez.client.widgets.session.presenter.impl;

import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.Window;
import org.uberfire.client.workbench.widgets.common.ErrorPopupPresenter;
import org.uberfire.mvp.Command;
import org.wirez.client.widgets.session.presenter.FullSessionPresenter;
import org.wirez.client.widgets.session.toolbar.ToolbarCommand;
import org.wirez.client.widgets.session.toolbar.ToolbarCommandCallback;
import org.wirez.client.widgets.session.toolbar.command.*;
import org.wirez.client.widgets.session.toolbar.impl.AbstractToolbar;
import org.wirez.core.client.api.ClientDefinitionManager;
import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.command.factory.CanvasCommandFactory;
import org.wirez.core.client.service.ClientDiagramServices;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.service.ServiceCallback;
import org.wirez.core.client.session.CanvasFullSession;
import org.wirez.core.client.session.impl.DefaultCanvasSessionManager;
import org.wirez.core.diagram.Diagram;
import org.wirez.core.diagram.Settings;
import org.wirez.core.graph.Element;

import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractFullSessionPresenter<S extends CanvasFullSession<AbstractCanvas, AbstractCanvasHandler>>
        extends AbstractReadOnlySessionPresenter<S>
        implements FullSessionPresenter<AbstractCanvas, AbstractCanvasHandler, S> {

    protected ClientDefinitionManager clientDefinitionManager;
    protected ClientFactoryServices clientFactoryServices;
    protected CanvasCommandFactory commandFactory;
    protected ClearCommand clearCommand;
    protected DeleteSelectionCommand deleteSelectionCommand;
    protected SaveCommand saveCommand;
    protected UndoCommand undoCommand;
    protected ValidateCommand validateCommand;

    private static Logger LOGGER = Logger.getLogger( FullSessionPresenter.class.getName() );

    @Inject
    public AbstractFullSessionPresenter( final DefaultCanvasSessionManager canvasSessionManager,
                                         final ClientDefinitionManager clientDefinitionManager,
                                         final ClientFactoryServices clientFactoryServices,
                                         final CanvasCommandFactory commandFactory,
                                         final ClientDiagramServices clientDiagramServices,
                                         final AbstractToolbar<S> toolbar,
                                         final ClearSelectionCommand clearSelectionCommand,
                                         final ClearCommand clearCommand,
                                         final DeleteSelectionCommand deleteSelectionCommand,
                                         final SaveCommand saveCommand,
                                         final UndoCommand undoCommand,
                                         final ValidateCommand validateCommand,
                                         final VisitGraphCommand visitGraphCommand,
                                         final SwitchGridCommand switchGridCommand,
                                         final ErrorPopupPresenter errorPopupPresenter,
                                         final View view ) {

        super( canvasSessionManager, clientDiagramServices, toolbar, clearSelectionCommand, visitGraphCommand,
                switchGridCommand, errorPopupPresenter, view );

        this.commandFactory = commandFactory;
        this.clientDefinitionManager = clientDefinitionManager;
        this.clientFactoryServices = clientFactoryServices;
        this.clearCommand = clearCommand;
        this.deleteSelectionCommand = deleteSelectionCommand;
        this.saveCommand = saveCommand;
        this.undoCommand = undoCommand;
        this.validateCommand = validateCommand;

    }

    @Override
    public void doInitialize( final S session,
                              final int width,
                              final int height ) {

        super.doInitialize( session, width, height );

        // Enable canvas controls.
        final AbstractCanvasHandler canvasHandler = getCanvasHandler();
        enableControl( session.getConnectionAcceptorControl(), canvasHandler );
        enableControl( session.getContainmentAcceptorControl(), canvasHandler );
        enableControl( session.getDockingAcceptorControl(), canvasHandler );
        enableControl( session.getDragControl(), canvasHandler );
        enableControl( session.getToolboxControl(), canvasHandler );
        enableControl( session.getBuilderControl(), canvasHandler );
        enableControl( session.getCanvasValidationControl(), canvasHandler );
        enableControl( session.getCanvasSaveControl(), canvasHandler );
        enableControl( session.getCanvasPaletteControl(), canvasHandler );
        enableControl( session.getCanvasNameEditionControl(), canvasHandler );

    }

    @Override
    protected void setToolbarCommands() {

        super.setToolbarCommands();

        // Toolbar commands for canvas controls.
        super.addToolbarCommand( ( ToolbarCommand<S> ) clearCommand );
        super.addToolbarCommand( ( ToolbarCommand<S> ) deleteSelectionCommand );
        super.addToolbarCommand( ( ToolbarCommand<S> ) validateCommand );
        super.addToolbarCommand( ( ToolbarCommand<S> ) saveCommand );
        super.addToolbarCommand( ( ToolbarCommand<S> ) undoCommand );

    }

    @Override
    protected void onUpdateElement( final Element element ) {

        super.onUpdateElement( element );

        fireRegistrationUpdateListeners( session.getConnectionAcceptorControl(), element );
        fireRegistrationUpdateListeners( session.getContainmentAcceptorControl(), element );
        fireRegistrationUpdateListeners( session.getDockingAcceptorControl(), element );
        fireRegistrationUpdateListeners( session.getDragControl(), element );
        fireRegistrationUpdateListeners( session.getToolboxControl(), element );
        fireRegistrationUpdateListeners( session.getBuilderControl(), element );
        fireRegistrationUpdateListeners( session.getCanvasPaletteControl(), element );
        fireRegistrationUpdateListeners( session.getCanvasNameEditionControl(), element );

    }

    @Override
    protected void onClear() {

        super.onClear();

        fireRegistrationClearListeners( session.getConnectionAcceptorControl() );
        fireRegistrationClearListeners( session.getContainmentAcceptorControl() );
        fireRegistrationClearListeners( session.getDockingAcceptorControl() );
        fireRegistrationClearListeners( session.getDragControl() );
        fireRegistrationClearListeners( session.getToolboxControl() );
        fireRegistrationClearListeners( session.getBuilderControl() );
        fireRegistrationClearListeners( session.getCanvasValidationControl() );
        fireRegistrationClearListeners( session.getCanvasSaveControl() );
        fireRegistrationClearListeners( session.getCanvasPaletteControl() );
        fireRegistrationClearListeners( session.getCanvasNameEditionControl() );

    }

    protected void onElementRegistration( final Element element,
                                          final boolean add ) {

        super.onElementRegistration( element, add );

        fireRegistrationListeners( session.getConnectionAcceptorControl(), element, add );
        fireRegistrationListeners( session.getContainmentAcceptorControl(), element, add );
        fireRegistrationListeners( session.getDockingAcceptorControl(), element, add );
        fireRegistrationListeners( session.getDragControl(), element, add );
        fireRegistrationListeners( session.getToolboxControl(), element, add );
        fireRegistrationListeners( session.getBuilderControl(), element, add );
        fireRegistrationListeners( session.getCanvasPaletteControl(), element, add );
        fireRegistrationListeners( session.getCanvasNameEditionControl(), element, add );

    }

    @Override
    @SuppressWarnings( "unchecked" )
    public void newDiagram( final String uuid,
                            final String title,
                            final String definitionSetId,
                            final String shapeSetId,
                            final Command callback ) {


        clientFactoryServices.newDiagram( uuid, definitionSetId, new ServiceCallback<Diagram>() {
            @Override
            public void onSuccess( final Diagram diagram ) {
                final Settings settings = diagram.getSettings();
                settings.setShapeSetId( shapeSetId );
                settings.setTitle( title );
                open( diagram, callback );
            }

            @Override
            public void onError( final ClientRuntimeError error ) {
                showError( error );
                callback.execute();
            }
        } );

    }

    @Override
    public void save( final Command callback ) {

        saveCommand.execute( new ToolbarCommandCallback<Diagram>() {

            @Override
            public void onCommandExecuted( final Diagram result ) {
                Window.alert( "Diagram saved successfully [UUID=" + result.getUUID() + "]" );
                callback.execute();
            }

            @Override
            public void onError( final ClientRuntimeError error ) {
                showError( error );
                callback.execute();
            }
        } );


    }

    @Override
    public void clear() {

        clearCommand.execute();

    }

    @Override
    public void undo() {

        undoCommand.execute();

    }

    @Override
    public void deleteSelected() {

        deleteSelectionCommand.execute();

    }

    @Override
    protected void disposeSession() {

        getCanvasHandler().clearRegistrationListeners();

        super.disposeSession();

        if ( null != clearCommand ) {
            this.clearCommand.destroy();
        }

        if ( null != deleteSelectionCommand ) {
            this.deleteSelectionCommand.destroy();
        }

        if ( null != saveCommand ) {
            this.saveCommand.destroy();
        }

        if ( null != undoCommand ) {
            this.undoCommand.destroy();
        }

        if ( null != validateCommand ) {
            this.validateCommand.destroy();
        }

        this.clientDefinitionManager = null;
        this.clientFactoryServices = null;
        this.commandFactory = null;
        this.clearCommand = null;
        this.deleteSelectionCommand = null;
        this.saveCommand = null;
        this.undoCommand = null;
        this.validateCommand = null;

    }

    @Override
    protected void pauseSession() {
    }

    /*
        PUBLIC UTILITY METHODS FOR CODING & TESTING
     */

    public void visitGraph() {

        visitGraphCommand.execute();

    }

    @Override
    protected void showError( String message ) {
        log( Level.SEVERE, message );
        super.showError( message );
    }

    private void log( final Level level, final String message ) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log( level, message );
        }
    }

}
