package org.wirez.core.client.canvas.event.command;

import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.command.CanvasViolation;
import org.wirez.core.client.canvas.event.AbstractCanvasHandlerEvent;
import org.wirez.core.command.Command;
import org.wirez.core.command.CommandResult;

import java.util.ArrayList;
import java.util.Collection;

public abstract class AbstractCanvasCommandEvent<H extends CanvasHandler> extends AbstractCanvasHandlerEvent<H> {

    private final Collection<Command<H, CanvasViolation>> commands;
    private final boolean isBatch;
    private final CommandResult<CanvasViolation> result;
    
    public AbstractCanvasCommandEvent(final H canvasHandler,
                                      final Command<H, CanvasViolation> command,
                                      final CommandResult<CanvasViolation> result) {
        super( canvasHandler );
        this.commands = new ArrayList<Command<H, CanvasViolation>>( 1 ) {{
            add( command );
        }};
        this.isBatch = false;
        this.result = result;
    }

    public AbstractCanvasCommandEvent(final H canvasHandler,
                                      final Collection<Command<H, CanvasViolation>> commands,
                                      final CommandResult<CanvasViolation> result) {
        super( canvasHandler );
        this.commands = commands;
        this.isBatch = true;
        this.result = result;
    }

    public Command<H, CanvasViolation> getCommand() {
        return isBatch ? null : commands.iterator().next();
    }

    public Collection<Command<H, CanvasViolation>> getCommands() {
        return isBatch ? commands : null;
    }

    public CommandResult<CanvasViolation> getResult() {
        return result;
    }
}
