package org.wirez.core.graph.command;

import org.wirez.core.command.Command;
import org.wirez.core.command.CommandManagerFactory;
import org.wirez.core.command.CommandResult;
import org.wirez.core.command.CommandUtils;
import org.wirez.core.command.batch.BatchCommandManager;
import org.wirez.core.command.batch.BatchCommandResult;
import org.wirez.core.command.event.local.CommandExecutedEvent;
import org.wirez.core.command.event.local.CommandUndoExecutedEvent;
import org.wirez.core.command.event.local.IsCommandAllowedEvent;
import org.wirez.core.rule.RuleViolation;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

@Dependent
public class GraphCommandManagerImpl
        implements GraphCommandManager {

    private final BatchCommandManager<GraphCommandExecutionContext, RuleViolation> batchCommandManager;
    private final Event<IsCommandAllowedEvent> isCommandAllowedEvent;
    private final Event<CommandExecutedEvent> commandExecutedEvent;
    private final Event<CommandUndoExecutedEvent> commandUndoExecutedEvent;

    protected GraphCommandManagerImpl() {
        this( null, null, null, null );
    }
    
    @Inject
    public GraphCommandManagerImpl( final CommandManagerFactory commandManagerFactory,
                                    final Event<IsCommandAllowedEvent> isCommandAllowedEvent,
                                    final Event<CommandExecutedEvent> commandExecutedEvent,
                                    final Event<CommandUndoExecutedEvent> commandUndoExecutedEvent ) {
        this.batchCommandManager = commandManagerFactory.newBatchCommandManager();
        this.isCommandAllowedEvent = isCommandAllowedEvent;
        this.commandExecutedEvent = commandExecutedEvent;
        this.commandUndoExecutedEvent = commandUndoExecutedEvent;
    }

    @Override
    public CommandResult<RuleViolation> allow( final GraphCommandExecutionContext context,
                                               final Command<GraphCommandExecutionContext, RuleViolation> command ) {

        final CommandResult<RuleViolation> result = batchCommandManager.allow( context, command );

        if ( null != isCommandAllowedEvent ) {

            isCommandAllowedEvent.fire( new IsCommandAllowedEvent( command, result ));

        }

        return result;
    }

    @Override
    public CommandResult<RuleViolation> execute( final GraphCommandExecutionContext context,
                                                 final Command<GraphCommandExecutionContext, RuleViolation> command ) {
        final CommandResult<RuleViolation> result = batchCommandManager.execute( context, command );

        if ( null != commandExecutedEvent ) {
            commandExecutedEvent.fire( new CommandExecutedEvent( command, result) );
        }

        return result;
    }

    @Override
    public BatchCommandManager<GraphCommandExecutionContext, RuleViolation> batch( final Command<GraphCommandExecutionContext, RuleViolation> command ) {
        return batchCommandManager.batch( command );
    }

    @Override
    public BatchCommandResult<RuleViolation> executeBatch( final GraphCommandExecutionContext context ) {
        final BatchCommandResult<RuleViolation> result = batchCommandManager.executeBatch( context );

        if ( null != commandExecutedEvent ) {
            commandExecutedEvent.fire( new CommandExecutedEvent( batchCommandManager.getBatchCommands(), result) );
        }

        return result;
    }

    @Override
    public Collection<Command<GraphCommandExecutionContext, RuleViolation>> getBatchCommands() {
        return batchCommandManager.getBatchCommands();
    }


    @Override
    public CommandResult<RuleViolation> undo( final GraphCommandExecutionContext context,
                                              final Command<GraphCommandExecutionContext, RuleViolation> command ) {

        final CommandResult<RuleViolation> result = batchCommandManager.undo( context, command );

        if ( null != commandUndoExecutedEvent ) {

            final CommandUndoExecutedEvent event = new CommandUndoExecutedEvent( command, result );

            commandUndoExecutedEvent.fire( event );
        }

        return result;
    }

    @Override
    public BatchCommandResult<RuleViolation> undoBatch( final GraphCommandExecutionContext context ) {

        final BatchCommandResult<RuleViolation> result = batchCommandManager.undoBatch( context );

        if ( null != commandUndoExecutedEvent ) {

            final CommandUndoExecutedEvent event = new CommandUndoExecutedEvent(
                    new ArrayList<>( batchCommandManager.getBatchCommands() ),
                    result );

            commandUndoExecutedEvent.fire( event );
        }

        return result;

    }
}
