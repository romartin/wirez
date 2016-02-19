package org.wirez.core.client.canvas.control;

import com.google.gwt.logging.client.LogConfiguration;
import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.command.CommandResults;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ParentChildRelationship;
import org.wirez.core.client.canvas.command.CanvasCommandViolation;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.impl.WiresCanvasHandler;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Dependent
public class ContainmentAcceptorImpl implements ContainmentAcceptor<WiresCanvasHandler> {

    private static Logger LOGGER = Logger.getLogger("org.wirez.core.client.canvas.control.ContainmentAcceptorImpl");

    CanvasCommandFactory commandFactory;

    @Inject
    public ContainmentAcceptorImpl(final CanvasCommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }
    
    @Override
    public boolean allow(final WiresCanvasHandler canvasHandler, 
                         final Node parent, 
                         final Node child) {

        final Edge current = isSameParent(parent, child);
        final Command<WiresCanvasHandler, CanvasCommandViolation> command = current != null ?
                commandFactory.SET_PARENT(parent, child, current) : commandFactory.ADD_CHILD(parent,child);
        final boolean isAllow = canvasHandler.getCommandManager().allow( canvasHandler, command );
        log(Level.FINE, "isAllow=" + isAllow);
        return isAllow;
    }

    @Override
    public boolean accept(final WiresCanvasHandler canvasHandler,
                          final Node parent, 
                          final Node child) {

        final Edge current = isSameParent(parent, child);
        final Command<WiresCanvasHandler, CanvasCommandViolation> command = current != null ? 
                commandFactory.SET_PARENT(parent, child, current) : commandFactory.ADD_CHILD(parent,child);
        final CommandResults<CanvasCommandViolation> violations = 
                canvasHandler.getCommandManager().execute( canvasHandler, command );;
        final boolean isAccept = isAccept(violations);
        log(Level.FINE, "isAccept=" + isAccept);
        return isAccept;
        
    }
    
    private Edge<ParentChildRelationship, Node> isSameParent(final Node parent,
                                 final Node child) {
        if ( null != parent && child != null) {
            final List<Edge> outEdges = parent.getOutEdges();
            if ( null != outEdges && !outEdges.isEmpty() ) {
                for ( final Edge edge : outEdges ) {
                    if ( edge.getContent() instanceof ParentChildRelationship ) {
                        final Node target = edge.getTargetNode();
                        if ( target != null && target.getUUID().equals(child.getUUID())) {
                            return (Edge<ParentChildRelationship, Node>) edge;
                        }
                    }
                }
            }
        }
        return null;
    }

    private boolean isAccept(final CommandResults<CanvasCommandViolation> results) {
        final boolean hasCommandErrors = results.results(CommandResult.Type.ERROR) != null
                && results.results(CommandResult.Type.ERROR).iterator().hasNext();
        return !hasCommandErrors;
    }

    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }
}
