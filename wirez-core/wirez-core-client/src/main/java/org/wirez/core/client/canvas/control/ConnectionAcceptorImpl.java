package org.wirez.core.client.canvas.control;

import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.command.CommandResults;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.client.canvas.command.CanvasCommandViolation;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.wires.WiresCanvasHandler;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class ConnectionAcceptorImpl implements ConnectionAcceptor<WiresCanvasHandler> {

    CanvasCommandFactory commandFactory;

    @Inject
    public ConnectionAcceptorImpl(final CanvasCommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    @Override
    public boolean allowSource(final WiresCanvasHandler canvasHandler,
                               final Node source, 
                               final Edge<View<?>, Node> connector, 
                               final int magnet) {
        return canvasHandler.allow( commandFactory.SET_SOURCE_NODE(source, connector, magnet) );
    }

    @Override
    public boolean allowTarget(final WiresCanvasHandler canvasHandler,
                               final Node target,
                               final Edge<View<?>, Node> connector,
                               final int magnet) {
        return canvasHandler.allow( commandFactory.SET_TARGET_NODE(target, connector, magnet) );
    }

    @Override
    public boolean acceptSource(final WiresCanvasHandler canvasHandler,
                                final Node source,
                                final Edge<View<?>, Node> connector,
                                final int magnet) {
        CommandResults<CanvasCommandViolation> violations = canvasHandler.execute( commandFactory.SET_SOURCE_NODE(source, connector, magnet) );
        return isAccept(violations);
    }

    @Override
    public boolean acceptTarget(final WiresCanvasHandler canvasHandler,
                                final Node target,
                                final Edge<View<?>, Node> connector,
                                final int magnet) {
        CommandResults<CanvasCommandViolation> violations = canvasHandler.execute( commandFactory.SET_TARGET_NODE(target, connector, magnet) );
        return isAccept(violations);
    }

    private boolean isAccept(final CommandResults<CanvasCommandViolation> results) {
        final boolean hasCommandErrors = results.results(CommandResult.Type.ERROR) != null
                && results.results(CommandResult.Type.ERROR).iterator().hasNext();
        return !hasCommandErrors;
    }
}
