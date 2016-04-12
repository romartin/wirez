package org.wirez.core.client.canvas.control;

import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.client.canvas.command.CanvasViolation;
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
        CommandResult<CanvasViolation> violations =
                canvasHandler.allow( commandFactory.SET_SOURCE_NODE(source, connector, magnet) );
        return isAccept(violations);
    }

    @Override
    public boolean allowTarget(final WiresCanvasHandler canvasHandler,
                               final Node target,
                               final Edge<View<?>, Node> connector,
                               final int magnet) {

        CommandResult<CanvasViolation> violations =
                canvasHandler.allow( commandFactory.SET_TARGET_NODE(target, connector, magnet) );
        return isAccept(violations);
    }

    @Override
    public boolean acceptSource(final WiresCanvasHandler canvasHandler,
                                final Node source,
                                final Edge<View<?>, Node> connector,
                                final int magnet) {
        CommandResult<CanvasViolation> violations =
                canvasHandler.execute( commandFactory.SET_SOURCE_NODE(source, connector, magnet) );
        return isAccept(violations);
    }

    @Override
    public boolean acceptTarget(final WiresCanvasHandler canvasHandler,
                                final Node target,
                                final Edge<View<?>, Node> connector,
                                final int magnet) {
        CommandResult<CanvasViolation> violations =
                canvasHandler.execute( commandFactory.SET_TARGET_NODE(target, connector, magnet) );
        return isAccept(violations);
    }

    private boolean isAccept(final CommandResult<CanvasViolation> result) {
        return !CommandResult.Type.ERROR.equals( result.getType() );
    }
}
