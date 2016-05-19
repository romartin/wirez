package org.wirez.core.command.event.global;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.command.Command;
import org.wirez.core.command.CommandResult;
import org.wirez.core.command.event.AbstractGraphCommandEvent;
import org.wirez.core.graph.command.GraphCommandExecutionContext;
import org.wirez.core.rule.RuleViolation;

@Portable
public final class CommandUndoExecutedEvent extends AbstractGraphCommandEvent {
    
    public CommandUndoExecutedEvent(@MapsTo("command") Command<GraphCommandExecutionContext, RuleViolation> command,
                                    @MapsTo("result") CommandResult<RuleViolation> result) {
        super(command, result);
    }
    
}
