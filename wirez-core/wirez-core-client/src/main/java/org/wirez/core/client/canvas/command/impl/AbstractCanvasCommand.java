package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.command.CanvasCommandResult;
import org.wirez.core.client.canvas.command.CanvasCommandViolation;
import org.wirez.core.client.canvas.impl.WiresCanvasHandler;

public abstract class AbstractCanvasCommand implements Command<WiresCanvasHandler, CanvasCommandViolation> {

    protected CanvasCommandFactory canvasCommandFactory;

    public AbstractCanvasCommand(final CanvasCommandFactory canvasCommandFactory) {
        this.canvasCommandFactory = canvasCommandFactory;
    }
    
    @Override
    public CommandResult<CanvasCommandViolation> allow(WiresCanvasHandler context) {
        return buildResult();
    }
    
    protected CommandResult<CanvasCommandViolation> buildResult() {
        return new CanvasCommandResult();
    }
}
