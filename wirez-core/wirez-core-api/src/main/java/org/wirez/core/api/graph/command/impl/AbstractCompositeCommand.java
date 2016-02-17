package org.wirez.core.api.graph.command.impl;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.command.GraphCommandFactory;
import org.wirez.core.api.graph.command.GraphCommandResult;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractCompositeCommand extends AbstractCommand {
    
    protected final List<Command<RuleManager, RuleViolation>> commands = new LinkedList<>();

    public AbstractCompositeCommand(GraphCommandFactory commandFactory) {
        super(commandFactory);
    }
    
    protected AbstractCompositeCommand addCommand(final Command<RuleManager, RuleViolation> command) {
        commands.add(command);
        return this;
    }

    
    @Override
    public CommandResult<RuleViolation> allow(final RuleManager context) {
        return allowAll(context);
    }

    protected CommandResult<RuleViolation> allowAll(final RuleManager context) {
        final List<CommandResult<RuleViolation>> results = new LinkedList<>();
        for ( final Command<RuleManager, RuleViolation> command : commands ) {
            final CommandResult<RuleViolation> violations = command.allow( context );
            results.add(violations);
        }

        return buildResult(results);
    }

    @Override
    public CommandResult<RuleViolation> execute(final RuleManager context) {
        final List<CommandResult<RuleViolation>> results = new LinkedList<>();
        for ( final Command<RuleManager, RuleViolation> command : commands ) {
            final CommandResult<RuleViolation> violations = command.execute( context );
            results.add(violations);
        }

        return buildResult(results);
    }

    @Override
    public CommandResult<RuleViolation> undo(final RuleManager context) {
        final List<CommandResult<RuleViolation>> results = new LinkedList<>();
        for ( final Command<RuleManager, RuleViolation> command : commands ) {
            final CommandResult<RuleViolation> violations = command.undo( context );
            results.add(violations);
        }

        return buildResult(results);
    }
    
    
    private CommandResult<RuleViolation> buildResult( final List<CommandResult<RuleViolation>> violations ) {
        if ( null != violations ) {
            final GraphCommandResult result = new GraphCommandResult();
            for (final CommandResult<RuleViolation> violation : violations) {
                Iterable<RuleViolation> violationsIter = violation.getViolations();
                for (RuleViolation ruleViolation : violationsIter) {
                    result.getViolationsCollection().add(ruleViolation);
                }
            }
            
            return result;
        }
        
        return null;
    }
    
}
