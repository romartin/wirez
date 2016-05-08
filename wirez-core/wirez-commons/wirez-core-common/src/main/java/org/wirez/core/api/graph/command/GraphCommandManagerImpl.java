package org.wirez.core.api.graph.command;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.command.CommandResultBuilder;
import org.wirez.core.api.command.batch.AbstractBatchCommandManager;
import org.wirez.core.api.event.local.CommandExecutedEvent;
import org.wirez.core.api.event.local.CommandUndoExecutedEvent;
import org.wirez.core.api.event.local.IsCommandAllowedEvent;
import org.wirez.core.api.rule.RuleViolation;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@ApplicationScoped
public class GraphCommandManagerImpl extends AbstractBatchCommandManager<GraphCommandExecutionContext, RuleViolation> 
        implements GraphCommandManager {

    Event<IsCommandAllowedEvent> isCommandAllowedEvent;
    Event<CommandExecutedEvent> commandExecutedEvent;
    Event<CommandUndoExecutedEvent> commandUndoExecutedEvent;

    protected GraphCommandManagerImpl() {
    }
    
    @Inject
    public GraphCommandManagerImpl(final Event<IsCommandAllowedEvent> isCommandAllowedEvent, 
                                   final Event<CommandExecutedEvent> commandExecutedEvent,
                                   final Event<CommandUndoExecutedEvent> commandUndoExecutedEvent) {
        this.isCommandAllowedEvent = isCommandAllowedEvent;
        this.commandExecutedEvent = commandExecutedEvent;
        this.commandUndoExecutedEvent = commandUndoExecutedEvent;
    }

    @Override
    protected CommandResult<RuleViolation> doAllow(final GraphCommandExecutionContext context, 
                                                   final Command<GraphCommandExecutionContext, RuleViolation> command) {
        final CommandResult<RuleViolation> result = super.doAllow(context, command);

        if ( null != isCommandAllowedEvent ) {
            isCommandAllowedEvent.fire( new IsCommandAllowedEvent( command, result ));
        }
        return result;
    }

    @Override
    protected CommandResult<RuleViolation> doExecute(final GraphCommandExecutionContext context,
                                                     final Command<GraphCommandExecutionContext, RuleViolation> command) {
        final CommandResult<RuleViolation> result = super.doExecute(context, command);
        
        if ( null != commandExecutedEvent ) {
            commandExecutedEvent.fire( new CommandExecutedEvent( command, result) );
        }
        
        return result;
    }

    @Override
    protected CommandResult<RuleViolation> doUndo(final GraphCommandExecutionContext context,
                                                  final Command<GraphCommandExecutionContext, RuleViolation> command) {
        final CommandResult<RuleViolation> result = super.doUndo(context, command);
        
        if ( null != commandUndoExecutedEvent ) {
            commandUndoExecutedEvent.fire( new CommandUndoExecutedEvent( command, result) );
        }
        
        return result;
    }

    @Override
    protected CommandResultBuilder<RuleViolation> buildCommandResultBuilder() {
        return new GraphCommandResultBuilder();
    }
    
}
