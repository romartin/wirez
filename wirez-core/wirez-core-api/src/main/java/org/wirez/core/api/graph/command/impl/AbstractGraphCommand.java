package org.wirez.core.api.graph.command.impl;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.command.GraphCommandExecutionContext;
import org.wirez.core.api.graph.command.GraphCommandResultBuilder;
import org.wirez.core.api.rule.RuleViolation;

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
