package org.kie.workbench.common.stunner.client.widgets.session.toolbar.command;

import org.gwtbootstrap3.client.ui.constants.IconType;
import org.kie.workbench.common.stunner.client.widgets.session.toolbar.Toolbar;
import org.kie.workbench.common.stunner.client.widgets.session.toolbar.ToolbarCommand;
import org.kie.workbench.common.stunner.client.widgets.session.toolbar.ToolbarCommandCallback;
import org.kie.workbench.common.stunner.core.client.command.CanvasViolation;
import org.kie.workbench.common.stunner.core.client.command.factory.CanvasCommandFactory;
import org.kie.workbench.common.stunner.core.client.canvas.event.command.CanvasCommandExecutedEvent;
import org.kie.workbench.common.stunner.core.client.canvas.event.command.CanvasUndoCommandExecutedEvent;
import org.kie.workbench.common.stunner.core.client.session.impl.DefaultCanvasFullSession;
import org.kie.workbench.common.stunner.core.command.CommandResult;
import org.kie.workbench.common.stunner.core.diagram.Diagram;
import org.kie.workbench.common.stunner.core.graph.Graph;
import org.kie.workbench.common.stunner.core.graph.Node;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Iterator;

import static org.uberfire.commons.validation.PortablePreconditions.checkNotNull;

@Dependent
public class ClearCommand extends AbstractToolbarCommand<DefaultCanvasFullSession> {

    CanvasCommandFactory canvasCommandFactory;

    @Inject
    public ClearCommand(final CanvasCommandFactory canvasCommandFactory) {
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
        return "Clear diagram";
    }

    @Override
    public <T> void execute(final ToolbarCommandCallback<T> callback) {

        executeWithConfirm( () -> {

            // Execute the clear canvas command.
            final CommandResult<CanvasViolation> result = session.getCanvasCommandManager().execute( session.getCanvasHandler(),
                    canvasCommandFactory.CLEAR_CANVAS() );

            if ( null != callback ) {
                callback.onCommandExecuted((T) result);
            }

        } );
        
    }

    @Override
    public ToolbarCommand<DefaultCanvasFullSession> initialize( final Toolbar<DefaultCanvasFullSession> toolbar,
                                                                final DefaultCanvasFullSession session ) {
        super.initialize( toolbar, session );
        checkState();
        return this;
    }

    @Override
    public void afterDraw() {
        super.afterDraw();
        checkState();
    }

    @SuppressWarnings( "unchecked" )
    protected boolean getState() {

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

        return doEnable;

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
