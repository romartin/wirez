package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.command.Command;
import org.wirez.core.command.CommandResult;
import org.wirez.core.graph.command.GraphCommandExecutionContext;
import org.wirez.core.graph.command.impl.ClearGraphCommand;
import org.wirez.core.rule.RuleViolation;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.AbstractCanvasGraphCommand;
import org.wirez.core.client.canvas.command.CanvasViolation;

public final class ClearCanvasCommand extends AbstractCanvasGraphCommand {

    @Override
    public CommandResult<CanvasViolation> execute(final AbstractCanvasHandler context) {
        context.clearCanvas();
        return buildResult();
    }

    @Override
    public CommandResult<CanvasViolation> undo(final AbstractCanvasHandler context) {
        // TODO: Return to previous snapshot?
        return null;
    }

    @Override
    protected Command<GraphCommandExecutionContext, RuleViolation> buildGraphCommand(AbstractCanvasHandler context) {
        return new ClearGraphCommand( context.getDiagram().getGraph() );
    }

}
