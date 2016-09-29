package org.kie.workbench.common.stunner.core.command.event;

import org.kie.workbench.common.stunner.core.command.CommandResult;
import org.kie.workbench.common.stunner.core.graph.command.GraphCommandExecutionContext;
import org.kie.workbench.common.stunner.core.rule.RuleViolation;
import org.kie.workbench.common.stunner.core.command.Command;

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
