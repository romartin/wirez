package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.command.GraphCommandExecutionContext;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.AbstractCanvasGraphCommand;
import org.wirez.core.client.canvas.command.CanvasViolation;

public final class UpdateCanvasElementPropertyCommand extends AbstractCanvasGraphCommand {

    protected Element element;
    protected String propertyId;
    protected Object value;
    
    public UpdateCanvasElementPropertyCommand(final Element element,
                                              final String propertyId,
                                              final Object value) {
        this.element = element;
        this.propertyId = propertyId;
        this.value = value;
    }

    @Override
    public CommandResult<CanvasViolation> execute(final AbstractCanvasHandler context) {
        context.updateElementProperties(element);
        return buildResult();
    }

    @Override
    public CommandResult<CanvasViolation> undo(final AbstractCanvasHandler context) {
        return execute( context );
    }

    @Override
    protected Command<GraphCommandExecutionContext, RuleViolation> buildGraphCommand(final AbstractCanvasHandler context) {
        return context.getGraphCommandFactory().UPDATE_PROPERTY_VALUE(element, propertyId, value);
    }
    
}
