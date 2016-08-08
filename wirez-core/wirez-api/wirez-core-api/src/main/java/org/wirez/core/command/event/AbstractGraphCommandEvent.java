package org.wirez.core.command.event;

import org.wirez.core.command.Command;
import org.wirez.core.command.CommandResult;
import org.wirez.core.graph.command.GraphCommandExecutionContext;
import org.wirez.core.rule.RuleViolation;

import java.util.Collection;

public abstract class AbstractGraphCommandEvent extends AbstractCommandEvent<GraphCommandExecutionContext, RuleViolation> {

    public AbstractGraphCommandEvent( final Command<GraphCommandExecutionContext, RuleViolation> command,
                                      final CommandResult<RuleViolation> result ) {
        super( command, result );
    }

    public AbstractGraphCommandEvent( final Collection<Command<GraphCommandExecutionContext, RuleViolation>> commands,
                                      final CommandResult<RuleViolation> result ) {
        super( commands, result );
    }
}
