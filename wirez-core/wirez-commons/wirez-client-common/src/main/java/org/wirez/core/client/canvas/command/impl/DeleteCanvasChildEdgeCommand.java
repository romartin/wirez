package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.command.Command;
import org.wirez.core.command.CommandResult;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.command.GraphCommandExecutionContext;
import org.wirez.core.graph.command.impl.DeleteChildEdgeCommand;
import org.wirez.core.rule.RuleViolation;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.AbstractCanvasCommand;
import org.wirez.core.client.canvas.command.AbstractCanvasGraphCommand;
import org.wirez.core.client.canvas.command.CanvasViolation;

public final class DeleteCanvasChildEdgeCommand extends AbstractCanvasGraphCommand {

    private final Node parent;
    private final Node child;

    public DeleteCanvasChildEdgeCommand(final Node parent, 
                                        final Node child) {
        this.parent = parent;
        this.child = child;
    }

    @Override
    public CommandResult<CanvasViolation> execute(final AbstractCanvasHandler context) {
        context.removeChild( parent.getUUID(), child.getUUID() );
        return buildResult();
    }

    @Override
    public CommandResult<CanvasViolation> undo(final AbstractCanvasHandler context) {
        AbstractCanvasCommand command = new AddCanvasChildEdgeCommand( parent, child );
        return command.execute( context );
    }

    @Override
    protected Command<GraphCommandExecutionContext, RuleViolation> buildGraphCommand(final AbstractCanvasHandler context) {
        return new DeleteChildEdgeCommand( parent, child );
    }

}
