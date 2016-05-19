package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.command.Command;
import org.wirez.core.command.CommandResult;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.command.GraphCommandExecutionContext;
import org.wirez.core.graph.command.impl.UpdateElementPropertyValueCommand;
import org.wirez.core.rule.RuleViolation;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.AbstractCanvasGraphCommand;
import org.wirez.core.client.canvas.command.CanvasViolation;
import org.wirez.core.client.shape.MutationContext;

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
        context.updateElementProperties(element, MutationContext.STATIC);
        return buildResult();
    }

    @Override
    public CommandResult<CanvasViolation> undo(final AbstractCanvasHandler context) {
        return execute( context );
    }

    @Override
    protected Command<GraphCommandExecutionContext, RuleViolation> buildGraphCommand(final AbstractCanvasHandler context) {
        return new UpdateElementPropertyValueCommand(element, propertyId, value);
    }
    
}
