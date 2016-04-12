package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.Element;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.AbstractCanvasGraphCommand;
import org.wirez.core.client.canvas.command.CanvasViolation;
import org.wirez.core.client.factory.ShapeFactory;

public abstract class AddCanvasElementCommand<E extends Element> extends AbstractCanvasGraphCommand {
    
    protected E candidate;
    protected ShapeFactory factory;

    public AddCanvasElementCommand(final E candidate, final ShapeFactory factory) {
        this.candidate = candidate;
        this.factory = factory;
    }

    @Override
    public CommandResult<CanvasViolation> execute(final AbstractCanvasHandler context) {
        doRegister(context);
        doMutate(context);
        return buildResult();
    }
    
    protected void doRegister(final AbstractCanvasHandler context) {
        context.register( factory, candidate );
    }

    protected void doMutate(final AbstractCanvasHandler context) {
        context.applyElementMutation( candidate );
    }
    
}
