package org.wirez.core.client.command;

import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.command.CommandResult;

public abstract class AbstractCanvasCommand implements CanvasCommand<AbstractCanvasHandler> {

    public AbstractCanvasCommand() {
    }
    
    @Override
    public CommandResult<CanvasViolation> allow( AbstractCanvasHandler context) {
        return buildResult();
    }
    
    protected CommandResult<CanvasViolation> buildResult() {
        return CanvasCommandResultBuilder.OK_COMMAND;
    }

}
