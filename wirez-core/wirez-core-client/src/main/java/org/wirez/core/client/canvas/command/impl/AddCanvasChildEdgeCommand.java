package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.GraphCommandExecutionContext;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.AbstractCanvasCommand;
import org.wirez.core.client.canvas.command.AbstractCanvasGraphCommand;
import org.wirez.core.client.canvas.command.CanvasViolation;

/**
 * TODO: Register the new edge into the canvas handler's index for the graph. 
 */
public final class AddCanvasChildEdgeCommand extends AbstractCanvasGraphCommand {

    protected Node parent;
    protected Node candidate;

    public AddCanvasChildEdgeCommand(final Node parent,
                                     final Node candidate) {
        this.parent = parent;
        this.candidate = candidate;
    }

    @Override
    public CommandResult<CanvasViolation> execute(final AbstractCanvasHandler context) {
        context.addChild(parent, candidate);
        context.applyElementMutation(candidate);
        return buildResult();
    }

    @Override
    public CommandResult<CanvasViolation> undo(final AbstractCanvasHandler context) {
        AbstractCanvasCommand command = context.getCommandFactory().DELETE_CHILD_EDGE( parent, candidate );
        return command.execute( context );
    }

    @Override
    protected Command<GraphCommandExecutionContext, RuleViolation> buildGraphCommand(AbstractCanvasHandler canvasHandler) {
        return canvasHandler.getGraphCommandFactory().ADD_CHILD_EDGE(parent, candidate);
    }

}
