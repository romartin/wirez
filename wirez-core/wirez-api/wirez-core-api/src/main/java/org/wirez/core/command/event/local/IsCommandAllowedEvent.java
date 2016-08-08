package org.wirez.core.command.event.local;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.wirez.core.command.Command;
import org.wirez.core.command.CommandResult;
import org.wirez.core.command.event.AbstractGraphCommandEvent;
import org.wirez.core.graph.command.GraphCommandExecutionContext;
import org.wirez.core.rule.RuleViolation;

import java.util.Collection;

@NonPortable
public final class IsCommandAllowedEvent extends AbstractGraphCommandEvent {

    public IsCommandAllowedEvent( final Command<GraphCommandExecutionContext, RuleViolation> command,
                                  final CommandResult<RuleViolation> result ) {
        super( command, result );
    }

    public IsCommandAllowedEvent( final Collection<Command<GraphCommandExecutionContext, RuleViolation>> commands,
                                  final CommandResult<RuleViolation> result ) {
        super( commands, result );
    }

}
