package org.kie.workbench.common.stunner.core.client.canvas.event.command;

import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;
import org.kie.workbench.common.stunner.core.client.command.CanvasViolation;
import org.kie.workbench.common.stunner.core.command.Command;
import org.kie.workbench.common.stunner.core.command.CommandResult;

import java.util.Collection;

public final class CanvasCommandAllowedEvent<H extends CanvasHandler> extends AbstractCanvasCommandEvent<H> {

    public CanvasCommandAllowedEvent( final H canvasHandler,
                                      final Command<H, CanvasViolation> command,
                                      final CommandResult<CanvasViolation> violation ) {
        super( canvasHandler, command, violation );
    }

    public CanvasCommandAllowedEvent( final H canvasHandler,
                                      final Collection<Command<H, CanvasViolation>> commands,
                                      final CommandResult<CanvasViolation> result ) {
        super( canvasHandler, commands, result );
    }

}
