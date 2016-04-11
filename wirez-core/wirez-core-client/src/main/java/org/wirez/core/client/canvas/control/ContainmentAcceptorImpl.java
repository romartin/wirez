package org.wirez.core.client.canvas.control;

import com.google.gwt.logging.client.LogConfiguration;
import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.command.CommandResults;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.relationship.Child;
import org.wirez.core.client.canvas.command.CanvasCommandViolation;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.wires.WiresCanvasHandler;

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

        boolean isAllow = false;
        final Edge childEdge = getChildEdge(child);
        final boolean isSameParent = isSameParent(parent, childEdge);
        if ( isSameParent ) {
            log(Level.FINE, "Is same parent. isAllow=true");
            isAllow = true;
        } else {
            final Command<WiresCanvasHandler, CanvasCommandViolation> command = commandFactory.ADD_CHILD_EDGE(parent,child);
            isAllow = canvasHandler.allow( command );
            log(Level.FINE, "isAllow=" + isAllow);
        }
        
        return isAllow;
    }

    @Override
    public boolean accept(final WiresCanvasHandler canvasHandler,
                          final Node parent, 
                          final Node child) {

        final Edge childEdge = getChildEdge(child);
        final boolean isSameParent = isSameParent(parent, childEdge);
        
        boolean isAccept = true;
        if ( !isSameParent ) {

            // Remove current child relationship.
            if ( null != childEdge ) {
                // TODO: Check command results?
                canvasHandler.execute( commandFactory.DELETE_CHILD_EDGE( childEdge.getSourceNode() , child ) );
            }

            // Add a new child relationship.
            final Command<WiresCanvasHandler, CanvasCommandViolation> command = commandFactory.ADD_CHILD_EDGE(parent,child);
            final CommandResults<CanvasCommandViolation> violations = canvasHandler.execute( command );
            isAccept = isAccept(violations);
            
        }
        
        log(Level.FINE, "isAccept=" + isAccept);
        return isAccept;
        
    }

    private boolean isSameParent(final Node parent,
                                   final Edge<Child, Node> edge) {

        if ( null != edge ) {
            final Node sourceNode = edge.getSourceNode();
            if ( sourceNode != null && sourceNode.getUUID().equals(parent.getUUID())) {
                return true;
            }
        }
        
        return parent == null;
    }

    private Edge<Child, Node> getChildEdge(final Node child) {
        if ( child != null) {
            final List<Edge> outEdges = child.getInEdges();
            if ( null != outEdges && !outEdges.isEmpty() ) {
                for ( final Edge edge : outEdges ) {
                    if ( edge.getContent() instanceof Child ) {
                        return edge;
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
