package org.wirez.core.api.graph.command.impl;

import org.wirez.core.api.command.AbstractCompositeCommand;
import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.command.CommandUtils;
import org.wirez.core.api.graph.command.EmptyRulesCommandExecutionContext;
import org.wirez.core.api.graph.command.GraphCommandExecutionContext;
import org.wirez.core.api.graph.command.GraphCommandResultBuilder;
import org.wirez.core.api.rule.RuleViolation;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractGraphCompositeCommand extends AbstractCompositeCommand<GraphCommandExecutionContext, RuleViolation> {

    @Override
    protected CommandResult<RuleViolation> doUndo(final GraphCommandExecutionContext context, 
                                                  final Command<GraphCommandExecutionContext, RuleViolation> command) {
        return command.undo( new EmptyRulesCommandExecutionContext( context ) );
    }

    @Override
    protected CommandResult<RuleViolation> doExecute(final GraphCommandExecutionContext context, 
                                                     final Command<GraphCommandExecutionContext, RuleViolation> command) {
        return command.execute( new EmptyRulesCommandExecutionContext( context ) );
    }

    @Override
    protected CommandResult<RuleViolation> buildResult( final List<CommandResult<RuleViolation>> violations ) {
        final Collection<RuleViolation> v = new LinkedList<>();
        for ( final CommandResult<RuleViolation> result : violations ) {
            v.addAll( CommandUtils.toList(result.getViolations()) );
        }
        return new GraphCommandResultBuilder( v ).build();
    }

}
