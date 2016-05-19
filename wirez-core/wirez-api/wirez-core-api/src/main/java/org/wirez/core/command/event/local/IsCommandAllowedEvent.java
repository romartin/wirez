package org.wirez.core.command.event.local;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.wirez.core.command.Command;
import org.wirez.core.command.CommandResult;
import org.wirez.core.command.event.AbstractGraphCommandEvent;
import org.wirez.core.graph.command.GraphCommandExecutionContext;
import org.wirez.core.rule.RuleViolation;

@NonPortable
public final class IsCommandAllowedEvent extends AbstractGraphCommandEvent {
    
    public IsCommandAllowedEvent(Command<GraphCommandExecutionContext, RuleViolation> command,
                                 CommandResult<RuleViolation> result) {
        super(command, result);
    }
    
}
