package org.wirez.client.widgets.session.toolbar.command;

import org.gwtbootstrap3.client.ui.constants.IconType;
import org.wirez.client.widgets.session.toolbar.ToolbarCommand;
import org.wirez.client.widgets.session.toolbar.ToolbarCommandCallback;
import org.wirez.client.widgets.session.toolbar.event.DisableToolbarCommandEvent;
import org.wirez.client.widgets.session.toolbar.event.EnableToolbarCommandEvent;
import org.wirez.core.client.canvas.command.CanvasViolation;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.event.command.CanvasCommandExecutedEvent;
import org.wirez.core.client.canvas.event.command.CanvasUndoCommandExecutedEvent;
import org.wirez.core.client.session.impl.DefaultCanvasFullSession;
import org.wirez.core.command.CommandResult;
import org.wirez.core.diagram.Diagram;
import org.wirez.core.graph.Graph;
import org.wirez.core.graph.Node;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Iterator;

import static org.uberfire.commons.validation.PortablePreconditions.checkNotNull;

@Dependent
public class ClearCommand extends AbstractToolbarCommand<DefaultCanvasFullSession> {

    CanvasCommandFactory canvasCommandFactory;

    @Inject
    public ClearCommand(final Event<EnableToolbarCommandEvent> enableToolbarCommandEvent,
                        final Event<DisableToolbarCommandEvent> disableToolbarCommandEvent,
                        final CanvasCommandFactory canvasCommandFactory) {
        super( enableToolbarCommandEvent, disableToolbarCommandEvent );
        this.canvasCommandFactory = canvasCommandFactory;
    }

    @Override
    public IconType getIcon() {
        return IconType.ERASER;
    }

    @Override
    public String getCaption() {
        return null;
    }

    @Override
    public String getTooltip() {
        return "Clear the diagram";
    }

    @Override
    public <T> void execute(final ToolbarCommandCallback<T> callback) {

        executeWithConfirm( () -> {

            // Execute the clear canvas command.
            final CommandResult<CanvasViolation> result = session.getCanvasCommandManager().execute( session.getCanvasHandler(),
                    canvasCommandFactory.CLEAR_CANVAS() );

            if ( null != callback ) {
                callback.onSuccess((T) result);
            }

        } );
        
    }

    @Override
    public ToolbarCommand<DefaultCanvasFullSession> initialize( final DefaultCanvasFullSession session ) {
        super.initialize( session );
        checkState();
        return this;
    }

    @Override
    public void afterDraw() {
        super.afterDraw();
        checkState();
    }

    @SuppressWarnings( "unchecked" )
    protected void checkState() {

        boolean doEnable = false;

        final Diagram diagram = null != session ? session.getCanvasHandler().getDiagram() : null;

        if ( null != diagram ) {

            final Graph graph = diagram.getGraph();

            if ( null != graph ) {

                final String rootUUID = diagram.getSettings().getCanvasRootUUID();

                Iterable<Node> nodes = graph.nodes();

                final boolean hasNodes = null != nodes && nodes.iterator().hasNext();

                if ( hasNodes ) {

                    final Iterator<Node> nodesIt = nodes.iterator();

                    final Node node = nodesIt.next();

                    if ( nodesIt.hasNext() ) {

                        doEnable = true;

                    } else  {

                        doEnable = null == rootUUID || !rootUUID.equals( node.getUUID() );
                    }

                }

            }

        }

        if ( doEnable ) {

            enable();

        } else  {

            disable();

        }

    }

    void onCommandExecuted(@Observes CanvasCommandExecutedEvent commandExecutedEvent) {
        checkNotNull("commandExecutedEvent", commandExecutedEvent);
        checkState();
    }

    void onCommandUndoExecuted(@Observes CanvasUndoCommandExecutedEvent commandUndoExecutedEvent) {
        checkNotNull("commandUndoExecutedEvent", commandUndoExecutedEvent);
        checkState();
    }
    
}
