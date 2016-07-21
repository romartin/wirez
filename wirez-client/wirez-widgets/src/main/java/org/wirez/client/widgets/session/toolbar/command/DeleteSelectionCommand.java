package org.wirez.client.widgets.session.toolbar.command;

import com.google.gwt.logging.client.LogConfiguration;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.wirez.client.widgets.session.toolbar.ToolbarCommandCallback;
import org.wirez.client.widgets.session.toolbar.event.DisableToolbarCommandEvent;
import org.wirez.client.widgets.session.toolbar.event.EnableToolbarCommandEvent;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.controls.select.SelectionControl;
import org.wirez.core.client.session.impl.DefaultCanvasFullSession;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.Node;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

@Dependent
public class DeleteSelectionCommand extends AbstractSelectionToolbarCommand<DefaultCanvasFullSession> {

    private static Logger LOGGER = Logger.getLogger( DeleteSelectionCommand.class.getName() );

    CanvasCommandFactory canvasCommandFactory;

    @Inject
    public DeleteSelectionCommand( final Event<EnableToolbarCommandEvent> enableToolbarCommandEvent,
                                   final Event<DisableToolbarCommandEvent> disableToolbarCommandEvent,
                                   final CanvasCommandFactory canvasCommandFactory ) {
        super( enableToolbarCommandEvent, disableToolbarCommandEvent );
        this.canvasCommandFactory = canvasCommandFactory;
    }

    @Override
    public IconType getIcon() {
        return IconType.TRASH_O;
    }

    @Override
    public String getCaption() {
        return null;
    }

    @Override
    public String getTooltip() {
        return "Delete the selected elements";
    }

    @Override
    public <T> void execute( final ToolbarCommandCallback<T> callback ) {

        if ( null != session.getShapeSelectionControl() ) {

            executeWithConfirm( () -> {

                final AbstractCanvasHandler canvasHandler = session.getCanvasHandler();
                final CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager = session.getCanvasCommandManager();
                final SelectionControl<AbstractCanvasHandler, Element> selectionControl = session.getShapeSelectionControl();

                final Collection<String> selectedItems = selectionControl.getSelectedItems();

                if ( selectedItems != null && !selectedItems.isEmpty() ) {

                    for ( final String selectedItemUUID : selectedItems ) {

                        Element element = canvasHandler.getGraphIndex().getNode( selectedItemUUID );

                        if ( element == null ) {

                            element = canvasHandler.getGraphIndex().getEdge( selectedItemUUID );

                            if ( element != null ) {
                                log( Level.FINE, "Deleting edge with id " + element.getUUID() );
                                canvasCommandManager.execute( canvasHandler, canvasCommandFactory.DELETE_EDGE( ( Edge ) element ) );
                            }

                        } else {
                            log( Level.FINE, "Deleting node with id " + element.getUUID() );
                            canvasCommandManager.execute( canvasHandler, canvasCommandFactory.DELETE_NODE( ( Node ) element ) );

                        }

                    }

                } else {

                    log( Level.FINE, "Cannot delete element, no element selected on canvas." );

                }

                if ( null != callback ) {
                    callback.onSuccess( null );
                }

            } );

        }

    }

    private void log( final Level level, final String message ) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log( level, message );
        }
    }

}
