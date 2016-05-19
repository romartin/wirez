package org.wirez.core.client.canvas.event.command;

import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.command.CanvasViolation;
import org.wirez.core.client.canvas.event.AbstractCanvasHandlerEvent;
import org.wirez.core.command.Command;
import org.wirez.core.command.CommandResult;

public abstract class AbstractCanvasCommandEvent<H extends CanvasHandler> extends AbstractCanvasHandlerEvent<H> {

    private final Command<H, CanvasViolation> command;
    private final CommandResult<CanvasViolation> result;
    
    public AbstractCanvasCommandEvent(final H canvasHandler,
                                      final Command<H, CanvasViolation> command,
                                      final CommandResult<CanvasViolation> result) {
        super( canvasHandler );
        this.command = command;
        this.result = result;
    }

    public Command<H, CanvasViolation> getCommand() {
        return command;
    }

    public CommandResult<CanvasViolation> getResult() {
        return result;
    }
}
