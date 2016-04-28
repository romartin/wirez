package org.wirez.core.api.event.local;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.event.command.AbstractGraphCommandEvent;
import org.wirez.core.api.graph.command.GraphCommandExecutionContext;
import org.wirez.core.api.rule.RuleViolation;

@NonPortable
public final class CommandUndoExecutedEvent extends AbstractGraphCommandEvent {
    
    public CommandUndoExecutedEvent(Command<GraphCommandExecutionContext, RuleViolation> command,
                                    CommandResult<RuleViolation> result) {
        super(command, result);
    }
    
}
