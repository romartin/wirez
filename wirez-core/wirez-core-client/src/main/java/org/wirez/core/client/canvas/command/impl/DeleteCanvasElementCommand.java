package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.Element;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.command.CanvasCommandViolation;
import org.wirez.core.client.canvas.impl.WiresCanvasHandler;
import org.wirez.core.client.factory.ShapeFactory;

public abstract class DeleteCanvasElementCommand<E extends Element> extends AbstractCanvasCommand {

    protected E candidate;
    protected ShapeFactory factory;

    public DeleteCanvasElementCommand(final CanvasCommandFactory canvasCommandFactory, final E candidate, final ShapeFactory factory) {
        super(canvasCommandFactory);
        this.candidate = candidate;
        this.factory = factory;
    }

    @Override
    public CommandResult<CanvasCommandViolation> execute(final WiresCanvasHandler context) {
        context.deregister(candidate);
        return buildResult();
    }

    @Override
    public CommandResult<CanvasCommandViolation> undo(final WiresCanvasHandler context) {
        //  TODO
        return null;
    }
}
