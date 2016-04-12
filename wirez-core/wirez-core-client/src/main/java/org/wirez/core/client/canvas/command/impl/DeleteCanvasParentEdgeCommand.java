package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.GraphCommandExecutionContext;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.AbstractCanvasGraphCommand;
import org.wirez.core.client.canvas.command.CanvasViolation;

public final class DeleteCanvasParentEdgeCommand extends AbstractCanvasGraphCommand {

    private final Node parent;
    private final Node child;

    public DeleteCanvasParentEdgeCommand(final Node parent,
                                         final Node child) {
        this.parent = parent;
        this.child = child;
    }


    @Override
    public CommandResult<CanvasViolation> execute(final AbstractCanvasHandler context) {
        context.removeChild(parent, child);
        return buildResult();
    }

    @Override
    public CommandResult<CanvasViolation> undo(final AbstractCanvasHandler context) {
        return context.getCommandFactory().ADD_PARENT_EDGE(parent, child).execute( context );
    }

    @Override
    protected Command<GraphCommandExecutionContext, RuleViolation> buildGraphCommand(final AbstractCanvasHandler context) {
        return context.getGraphCommandFactory().DELETE_PARENT_EDGE( parent, child );
    }

}
