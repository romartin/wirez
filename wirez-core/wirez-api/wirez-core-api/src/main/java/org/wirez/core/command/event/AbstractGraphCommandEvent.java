package org.wirez.core.command.event;

import org.wirez.core.command.Command;
import org.wirez.core.command.CommandResult;
import org.wirez.core.graph.command.GraphCommandExecutionContext;
import org.wirez.core.rule.RuleViolation;

public abstract class AbstractGraphCommandEvent extends AbstractCommandEvent<GraphCommandExecutionContext, RuleViolation> {
    
    public AbstractGraphCommandEvent(Command<GraphCommandExecutionContext, RuleViolation> command,
                                     CommandResult<RuleViolation> result) {
        super(command, result);
    }
    
}
