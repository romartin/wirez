package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.AbstractCanvasGraphCommand;
import org.wirez.core.client.canvas.command.CanvasViolation;
import org.wirez.core.client.shape.MutationContext;
import org.wirez.core.command.Command;
import org.wirez.core.command.CommandResult;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.command.GraphCommandExecutionContext;
import org.wirez.core.graph.command.impl.AddDockEdgeCommand;
import org.wirez.core.rule.RuleViolation;

public final class AddCanvasDockEdgeCommand extends AbstractCanvasGraphCommand {

    protected Node parent;
    protected Node candidate;

    public AddCanvasDockEdgeCommand(final Node parent,
                                    final Node candidate) {
        this.parent = parent;
        this.candidate = candidate;
    }

    @Override
    public CommandResult<CanvasViolation> execute(final AbstractCanvasHandler context) {
        context.dock( parent, candidate );
        context.applyElementMutation( candidate, MutationContext.STATIC );
        return buildResult();
    }

    @Override
    public CommandResult<CanvasViolation> undo(final AbstractCanvasHandler context) {
        final DeleteCanvasDockEdgeCommand command = new DeleteCanvasDockEdgeCommand( parent, candidate );
        return command.execute( context );
    }

    @Override
    protected Command<GraphCommandExecutionContext, RuleViolation> buildGraphCommand(final AbstractCanvasHandler context) {
        return new AddDockEdgeCommand( parent, candidate );
    }

}
