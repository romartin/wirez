package org.wirez.core.graph.command.impl;

import org.wirez.core.command.AbstractCompositeCommand;
import org.wirez.core.command.Command;
import org.wirez.core.command.CommandResult;
import org.wirez.core.command.CommandUtils;
import org.wirez.core.graph.command.EmptyRulesCommandExecutionContext;
import org.wirez.core.graph.command.GraphCommandExecutionContext;
import org.wirez.core.graph.command.GraphCommandResultBuilder;
import org.wirez.core.rule.RuleViolation;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractGraphCompositeCommand extends AbstractCompositeCommand<GraphCommandExecutionContext, RuleViolation> {

    protected abstract CommandResult<RuleViolation> doCheck(final GraphCommandExecutionContext context);

    @Override
    protected CommandResult<RuleViolation> doUndo(final GraphCommandExecutionContext context, 
                                                  final Command<GraphCommandExecutionContext, RuleViolation> command) {
        return command.undo( buildEmptyExecutionContext( context ) );
    }

    @Override
    protected CommandResult<RuleViolation> doExecute(final GraphCommandExecutionContext context, 
                                                     final Command<GraphCommandExecutionContext, RuleViolation> command) {
        return command.execute( buildEmptyExecutionContext( context ) );
    }

    @Override
    protected CommandResult<RuleViolation> buildResult( final List<CommandResult<RuleViolation>> violations ) {
        final Collection<RuleViolation> v = new LinkedList<>();
        for ( final CommandResult<RuleViolation> result : violations ) {
            v.addAll( CommandUtils.toList(result.getViolations()) );
        }
        return new GraphCommandResultBuilder( v ).build();
    }

    protected CommandResult<RuleViolation> check(final GraphCommandExecutionContext context) {

        // Check if rules are present.
        if ( null == context.getRulesManager() ) {
            return GraphCommandResultBuilder.RESULT_OK;
        }

        return doCheck( context );
    }

    private EmptyRulesCommandExecutionContext buildEmptyExecutionContext(final GraphCommandExecutionContext context ) {
        return new EmptyRulesCommandExecutionContext( context.getDefinitionManager(), 
                context.getFactoryManager() );
    }
}
