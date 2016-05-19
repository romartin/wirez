package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.command.Command;
import org.wirez.core.command.CommandResult;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.command.GraphCommandExecutionContext;
import org.wirez.core.graph.command.impl.AddParentEdgeCommand;
import org.wirez.core.rule.RuleViolation;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.AbstractCanvasGraphCommand;
import org.wirez.core.client.canvas.command.CanvasViolation;
import org.wirez.core.client.shape.MutationContext;

public final class AddCanvasParentEdgeCommand extends AbstractCanvasGraphCommand{

    protected Node parent;
    protected Node candidate;

    public AddCanvasParentEdgeCommand(final Node parent,
                                      final Node candidate) {
        this.parent = parent;
        this.candidate = candidate;
    }

    @Override
    public CommandResult<CanvasViolation> execute(final AbstractCanvasHandler context) {
        context.addChild(parent, candidate);
        context.applyElementMutation(candidate, MutationContext.STATIC);
        return buildResult();
    }

    @Override
    public CommandResult<CanvasViolation> undo(final AbstractCanvasHandler context) {
        final DeleteCanvasParentEdgeCommand command = new DeleteCanvasParentEdgeCommand( parent, candidate );
        return command.execute( context );
    }

    @Override
    protected Command<GraphCommandExecutionContext, RuleViolation> buildGraphCommand(final AbstractCanvasHandler context) {
        return new AddParentEdgeCommand(parent, candidate);
    }

}
