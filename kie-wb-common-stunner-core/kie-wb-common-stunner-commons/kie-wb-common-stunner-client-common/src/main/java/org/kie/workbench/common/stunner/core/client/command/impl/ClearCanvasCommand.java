package org.kie.workbench.common.stunner.core.client.command.impl;

import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.client.command.AbstractCanvasGraphCommand;
import org.kie.workbench.common.stunner.core.client.command.CanvasViolation;
import org.kie.workbench.common.stunner.core.command.Command;
import org.kie.workbench.common.stunner.core.command.CommandResult;
import org.kie.workbench.common.stunner.core.graph.command.GraphCommandExecutionContext;
import org.kie.workbench.common.stunner.core.graph.command.impl.ClearGraphCommand;
import org.kie.workbench.common.stunner.core.rule.RuleViolation;

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
