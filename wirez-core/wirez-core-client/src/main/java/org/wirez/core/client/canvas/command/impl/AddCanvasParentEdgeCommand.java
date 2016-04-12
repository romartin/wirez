package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.GraphCommandExecutionContext;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.AbstractCanvasGraphCommand;
import org.wirez.core.client.canvas.command.CanvasViolation;

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
        context.applyElementMutation(candidate);
        return buildResult();
    }

    @Override
    public CommandResult<CanvasViolation> undo(final AbstractCanvasHandler context) {
        return context.getCommandFactory().DELETE_PARENT_EDGE(parent, candidate).execute( context );
    }

    @Override
    protected Command<GraphCommandExecutionContext, RuleViolation> buildGraphCommand(final AbstractCanvasHandler context) {
        return context.getGraphCommandFactory().ADD_PARENT_EDGE(parent, candidate);
    }

}
