package org.kie.workbench.common.stunner.core.client.command;

import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.command.CommandResult;

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
