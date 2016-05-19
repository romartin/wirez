package org.wirez.core.graph.command.impl;

import org.wirez.core.command.Command;
import org.wirez.core.command.CommandResult;
import org.wirez.core.graph.command.GraphCommandExecutionContext;
import org.wirez.core.graph.command.GraphCommandResultBuilder;
import org.wirez.core.rule.RuleViolation;

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
