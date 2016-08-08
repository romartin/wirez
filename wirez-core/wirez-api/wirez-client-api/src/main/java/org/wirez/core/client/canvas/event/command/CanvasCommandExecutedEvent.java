package org.wirez.core.client.canvas.event.command;

import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.command.CanvasViolation;
import org.wirez.core.command.Command;
import org.wirez.core.command.CommandResult;

import java.util.Collection;

public final class CanvasCommandExecutedEvent<H extends CanvasHandler> extends AbstractCanvasCommandEvent<H> {

    public CanvasCommandExecutedEvent( final H canvasHandler,
                                       final Command<H, CanvasViolation> command,
                                       final CommandResult<CanvasViolation> violation ) {
        super( canvasHandler, command, violation );
    }

    public CanvasCommandExecutedEvent( final H canvasHandler,
                                       final Collection<Command<H, CanvasViolation>> commands,
                                       final CommandResult<CanvasViolation> result ) {
        super( canvasHandler, commands, result );
    }
}
