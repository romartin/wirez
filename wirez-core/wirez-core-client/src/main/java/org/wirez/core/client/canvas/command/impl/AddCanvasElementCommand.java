package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.Element;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.command.CanvasCommandViolation;
import org.wirez.core.client.canvas.wires.WiresCanvasHandler;
import org.wirez.core.client.factory.ShapeFactory;

public abstract class AddCanvasElementCommand<E extends Element> extends AbstractCanvasCommand {
    
    protected E candidate;
    protected ShapeFactory factory;

    public AddCanvasElementCommand(final CanvasCommandFactory canvasCommandFactory, final E candidate, final ShapeFactory factory) {
        super(canvasCommandFactory);
        this.candidate = candidate;
        this.factory = factory;
    }

    @Override
    public CommandResult<CanvasCommandViolation> execute(final WiresCanvasHandler context) {
        doRegister(context);
        doMutate(context);
        return buildResult();
    }
    
    protected void doRegister(final WiresCanvasHandler context) {
        context.register(factory, candidate);
    }

    protected void doMutate(final WiresCanvasHandler context) {
        context.applyElementMutation(candidate);
    }
    
}
