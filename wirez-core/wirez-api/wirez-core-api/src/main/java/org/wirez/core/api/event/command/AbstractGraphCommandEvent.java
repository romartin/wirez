package org.wirez.core.api.event.command;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.command.GraphCommandExecutionContext;
import org.wirez.core.api.rule.RuleViolation;

public abstract class AbstractGraphCommandEvent extends AbstractCommandEvent<GraphCommandExecutionContext, RuleViolation> {
    
    public AbstractGraphCommandEvent(Command<GraphCommandExecutionContext, RuleViolation> command,
                                     CommandResult<RuleViolation> result) {
        super(command, result);
    }
    
}
