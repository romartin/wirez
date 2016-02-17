package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.Element;
import org.wirez.core.client.canvas.command.CanvasCommandFactory;
import org.wirez.core.client.canvas.command.CanvasCommandViolation;
import org.wirez.core.client.canvas.impl.WiresCanvasHandler;

public class UpdateCanvasElementPositionCommand extends AbstractCanvasCommand {

    protected Element element;

    public UpdateCanvasElementPositionCommand(final CanvasCommandFactory canvasCommandFactory, final Element element) {
        super(canvasCommandFactory);
        this.element = element;
    }

    @Override
    public CommandResult<CanvasCommandViolation> execute(final WiresCanvasHandler context) {
        context.updateElementPosition(element);
        return buildResult();
    }

    @Override
    public CommandResult<CanvasCommandViolation> undo(final WiresCanvasHandler context) {
        // TODO
        return null;
    }
}
