package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.api.command.CommandResult;
import org.wirez.core.client.canvas.command.CanvasCommandFactory;
import org.wirez.core.client.canvas.command.CanvasCommandViolation;
import org.wirez.core.client.canvas.impl.WiresCanvasHandler;

public class ClearCanvasCommand extends AbstractCanvasCommand {

    public ClearCanvasCommand(CanvasCommandFactory canvasCommandFactory) {
        super(canvasCommandFactory);
    }

    @Override
    public CommandResult<CanvasCommandViolation> execute(final WiresCanvasHandler context) {
        context.clear();
        return buildResult();
    }

    @Override
    public CommandResult<CanvasCommandViolation> undo(final WiresCanvasHandler context) {
        // TODO
        return null;
    }
}
