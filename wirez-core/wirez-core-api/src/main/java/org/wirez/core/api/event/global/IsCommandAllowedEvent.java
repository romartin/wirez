package org.wirez.core.api.event.global;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.event.command.AbstractGraphCommandEvent;
import org.wirez.core.api.graph.command.GraphCommandExecutionContext;
import org.wirez.core.api.rule.RuleViolation;

@Portable
public final class IsCommandAllowedEvent extends AbstractGraphCommandEvent {
    
    public IsCommandAllowedEvent(@MapsTo("command") Command<GraphCommandExecutionContext, RuleViolation> command,
                                 @MapsTo("result") CommandResult<RuleViolation> result) {
        super(command, result);
    }
    
}
