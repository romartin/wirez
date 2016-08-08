package org.wirez.core.client.command.impl;

import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.command.AbstractCanvasGraphCommand;
import org.wirez.core.client.command.CanvasViolation;
import org.wirez.core.client.shape.MutationContext;
import org.wirez.core.command.Command;
import org.wirez.core.command.CommandResult;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.command.GraphCommandExecutionContext;
import org.wirez.core.graph.command.impl.UpdateElementPropertyValueCommand;
import org.wirez.core.rule.RuleViolation;

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
    public CommandResult<CanvasViolation> doExecute(final AbstractCanvasHandler context) {
        context.updateElementProperties(element, MutationContext.STATIC);
        return buildResult();
    }

    @Override
    public CommandResult<CanvasViolation> doUndo(final AbstractCanvasHandler context) {
        return doExecute( context );
    }

    @Override
    protected Command<GraphCommandExecutionContext, RuleViolation> buildGraphCommand(final AbstractCanvasHandler context) {
        return new UpdateElementPropertyValueCommand(element, propertyId, value);
    }
    
}
