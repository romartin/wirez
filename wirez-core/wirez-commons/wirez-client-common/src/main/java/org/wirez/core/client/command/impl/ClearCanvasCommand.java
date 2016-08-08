package org.wirez.core.client.command.impl;

import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.command.AbstractCanvasGraphCommand;
import org.wirez.core.client.command.CanvasViolation;
import org.wirez.core.command.Command;
import org.wirez.core.command.CommandResult;
import org.wirez.core.graph.command.GraphCommandExecutionContext;
import org.wirez.core.graph.command.impl.ClearGraphCommand;
import org.wirez.core.rule.RuleViolation;

public final class ClearCanvasCommand extends AbstractCanvasGraphCommand {

    @Override
    protected CommandResult<CanvasViolation> doExecute( final AbstractCanvasHandler context ) {
        context.clearCanvas();
        return buildResult();
    }

    @Override
    public CommandResult<CanvasViolation> doUndo(final AbstractCanvasHandler context) {
        // TODO: Return to previous snapshot?
        return null;
    }

    @Override
    protected Command<GraphCommandExecutionContext, RuleViolation> buildGraphCommand( final AbstractCanvasHandler context ) {

        final String rootUUID = context.getDiagram().getSettings().getCanvasRootUUID();

        return new ClearGraphCommand( context.getDiagram().getGraph(), rootUUID );

    }

}
