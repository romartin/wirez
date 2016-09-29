package org.kie.workbench.common.stunner.core.graph.command.impl;

import org.kie.workbench.common.stunner.core.command.Command;
import org.kie.workbench.common.stunner.core.command.CommandResult;
import org.kie.workbench.common.stunner.core.graph.command.GraphCommandExecutionContext;
import org.kie.workbench.common.stunner.core.graph.command.GraphCommandResultBuilder;
import org.kie.workbench.common.stunner.core.rule.RuleViolation;

public abstract class AbstractGraphCommand implements Command<GraphCommandExecutionContext, RuleViolation> {

    protected abstract CommandResult<RuleViolation> doCheck(final GraphCommandExecutionContext context);
    
    protected CommandResult<RuleViolation> check(final GraphCommandExecutionContext context) {
        
        // Check if rules are present.
        if ( null == context.getRulesManager() ) {
            return GraphCommandResultBuilder.RESULT_OK;
        }
        
        return doCheck( context );
    }
    
}
