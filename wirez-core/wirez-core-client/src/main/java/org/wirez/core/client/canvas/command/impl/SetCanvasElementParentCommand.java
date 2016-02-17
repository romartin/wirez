package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.Node;
import org.wirez.core.client.canvas.command.CanvasCommandFactory;
import org.wirez.core.client.canvas.command.CanvasCommandViolation;
import org.wirez.core.client.canvas.impl.WiresCanvasHandler;

public class SetCanvasElementParentCommand extends AbstractCanvasCommand {

    protected Node parent;
    protected Node candidate;

    public SetCanvasElementParentCommand(final CanvasCommandFactory canvasCommandFactory, 
                                         final Node parent, 
                                         final Node candidate) {
        super(canvasCommandFactory);
        this.parent = parent;
        this.candidate = candidate;
    }

    @Override
    public CommandResult<CanvasCommandViolation> execute(final WiresCanvasHandler context) {
        context.addChild(parent, candidate);
        context.applyElementMutation(candidate);
        return buildResult();
    }

    @Override
    public CommandResult<CanvasCommandViolation> undo(final WiresCanvasHandler context) {
        // TODO
        return null;
    }
}
