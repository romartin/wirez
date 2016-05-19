package org.wirez.core.client.canvas.command;

import org.wirez.core.command.CommandResult;
import org.wirez.core.client.canvas.AbstractCanvasHandler;

public abstract class AbstractCanvasCommand implements CanvasCommand<AbstractCanvasHandler> {

    public AbstractCanvasCommand() {
    }
    
    @Override
    public CommandResult<CanvasViolation> allow(AbstractCanvasHandler context) {
        return buildResult();
    }
    
    protected CommandResult<CanvasViolation> buildResult() {
        return CanvasCommandResultBuilder.OK_COMMAND;
    }

}
