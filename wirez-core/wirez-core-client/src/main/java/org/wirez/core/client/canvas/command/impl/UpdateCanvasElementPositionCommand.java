package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.command.GraphCommandExecutionContext;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.AbstractCanvasGraphCommand;
import org.wirez.core.client.canvas.command.CanvasViolation;

public final class UpdateCanvasElementPositionCommand extends AbstractCanvasGraphCommand {

    protected Element element;

    Double x;
    Double y;
    
    public UpdateCanvasElementPositionCommand(final Element element,
                                              final Double x,
                                              final Double y) {
        this.element = element;
        this.x = x;
        this.y = y;
    }

    @Override
    public CommandResult<CanvasViolation> execute(final AbstractCanvasHandler context) {
        context.updateElementPosition(element);
        return buildResult();
    }

    @Override
    public CommandResult<CanvasViolation> undo(final AbstractCanvasHandler context) {
        return execute( context );
    }

    @Override
    protected Command<GraphCommandExecutionContext, RuleViolation> buildGraphCommand(final AbstractCanvasHandler context) {
        return context.getGraphCommandFactory().UPDATE_POSITION( element, x, y );
    }

}
