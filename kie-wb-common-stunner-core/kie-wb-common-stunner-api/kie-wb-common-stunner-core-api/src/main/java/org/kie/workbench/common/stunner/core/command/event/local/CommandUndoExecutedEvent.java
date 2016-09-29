package org.kie.workbench.common.stunner.core.command.event.local;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.kie.workbench.common.stunner.core.command.Command;
import org.kie.workbench.common.stunner.core.command.CommandResult;
import org.kie.workbench.common.stunner.core.command.event.AbstractGraphCommandEvent;
import org.kie.workbench.common.stunner.core.graph.command.GraphCommandExecutionContext;
import org.kie.workbench.common.stunner.core.rule.RuleViolation;

import java.util.Collection;

@NonPortable
public final class CommandUndoExecutedEvent extends AbstractGraphCommandEvent {

    public CommandUndoExecutedEvent( final Command<GraphCommandExecutionContext, RuleViolation> command,
                                     final CommandResult<RuleViolation> result ) {
        super( command, result );
    }

    public CommandUndoExecutedEvent( final Collection<Command<GraphCommandExecutionContext, RuleViolation>> commands,
                                     final CommandResult<RuleViolation> result ) {
        super( commands, result );
    }
}
