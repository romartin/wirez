package org.wirez.core.client.command.impl;

import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.command.AbstractCanvasGraphCommand;
import org.wirez.core.client.command.CanvasViolation;
import org.wirez.core.command.Command;
import org.wirez.core.command.CommandResult;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.command.GraphCommandExecutionContext;
import org.wirez.core.graph.command.impl.DeleteDockEdgeCommand;
import org.wirez.core.rule.RuleViolation;

public final class DeleteCanvasDockEdgeCommand extends AbstractCanvasGraphCommand {

    private final Node parent;
    private final Node child;

    public DeleteCanvasDockEdgeCommand(final Node parent,
                                       final Node child) {
        this.parent = parent;
        this.child = child;
    }


    @Override
    public CommandResult<CanvasViolation> doExecute(final AbstractCanvasHandler context) {
        context.undock( parent.getUUID(), child.getUUID() );
        return buildResult();
    }

    @Override
    public CommandResult<CanvasViolation> doUndo(final AbstractCanvasHandler context) {
        return new AddCanvasDockEdgeCommand( parent, child ).execute( context );
    }

    @Override
    protected Command<GraphCommandExecutionContext, RuleViolation> buildGraphCommand(final AbstractCanvasHandler context) {
        return new DeleteDockEdgeCommand( parent, child );
    }

}
