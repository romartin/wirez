package org.wirez.core.client.canvas.control;

import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.command.CommandResults;
import org.wirez.core.api.graph.Node;
import org.wirez.core.client.canvas.command.CanvasCommandViolation;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.impl.WiresCanvasHandler;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class ContainmentAcceptorImpl implements ContainmentAcceptor<WiresCanvasHandler> {

    CanvasCommandFactory commandFactory;

    @Inject
    public ContainmentAcceptorImpl(final CanvasCommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }
    
    @Override
    public boolean allow(final WiresCanvasHandler canvasHandler, 
                         final Node parent, 
                         final Node child) {
        return canvasHandler.getCommandManager().allow( canvasHandler, commandFactory.ADD_CHILD(parent,child) );
    }

    @Override
    public boolean accept(final WiresCanvasHandler canvasHandler,
                          final Node parent, 
                          final Node child) {
        final CommandResults<CanvasCommandViolation> violations = 
                canvasHandler.getCommandManager().execute( canvasHandler, commandFactory.ADD_CHILD(parent,child) );;
        return isAccept(violations);
    }

    private boolean isAccept(final CommandResults<CanvasCommandViolation> results) {
        final boolean hasCommandErrors = results.results(CommandResult.Type.ERROR) != null
                && results.results(CommandResult.Type.ERROR).iterator().hasNext();
        return !hasCommandErrors;
    }
}
