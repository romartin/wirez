package org.wirez.core.client.command.impl;

import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.command.AbstractCanvasCommand;
import org.wirez.core.client.command.CanvasViolation;
import org.wirez.core.client.shape.MutationContext;
import org.wirez.core.command.CommandResult;
import org.wirez.core.graph.Element;

public final class UpdateCanvasElementPropertiesCommand extends AbstractCanvasCommand {

    protected Element element;
    
    public UpdateCanvasElementPropertiesCommand(final Element element) {
        this.element = element;
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

}
