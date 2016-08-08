package org.wirez.core.client.command;

import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.event.command.CanvasCommandAllowedEvent;
import org.wirez.core.client.canvas.event.command.CanvasCommandExecutedEvent;
import org.wirez.core.client.canvas.event.command.CanvasUndoCommandExecutedEvent;
import org.wirez.core.command.Command;
import org.wirez.core.command.CommandManagerFactory;
import org.wirez.core.command.CommandResult;
import org.wirez.core.command.batch.BatchCommandManager;
import org.wirez.core.command.batch.BatchCommandResult;
import org.wirez.core.command.stack.StackCommandManager;
import org.wirez.core.registry.command.CommandRegistry;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.Collection;

@Dependent
public class CanvasStackCommandManager implements CanvasCommandManager<AbstractCanvasHandler>, StackCommandManager<AbstractCanvasHandler, CanvasViolation> {

    private final StackCommandManager<AbstractCanvasHandler, CanvasViolation> stackCommandManager;

    protected CanvasStackCommandManager()  {
        this.stackCommandManager = null;
    }

    @Inject
    public CanvasStackCommandManager( final CommandManagerFactory commandManagerFactory,
                                      final Event<CanvasCommandAllowedEvent> isCanvasCommandAllowedEvent,
                                      final Event<CanvasCommandExecutedEvent> canvasCommandExecutedEvent,
                                      final Event<CanvasUndoCommandExecutedEvent> canvasUndoCommandExecutedEvent ) {
        this.stackCommandManager =
                commandManagerFactory
                        .newStackCommandManagerFor(
                                new CanvasCommandManagerImpl( isCanvasCommandAllowedEvent,
                                        canvasCommandExecutedEvent,
                                        canvasUndoCommandExecutedEvent,
                                        commandManagerFactory ) );
    }


    @Override
    public CommandRegistry<Command<AbstractCanvasHandler, CanvasViolation>> getRegistry() {
        return stackCommandManager.getRegistry();
    }

    @Override
    public BatchCommandManager<AbstractCanvasHandler, CanvasViolation> batch( final Command<AbstractCanvasHandler, CanvasViolation> command ) {
        return stackCommandManager.batch( command );
    }

    @Override
    public BatchCommandResult<CanvasViolation> executeBatch( final AbstractCanvasHandler context ) {
        return stackCommandManager.executeBatch( context );
    }

    @Override
    public BatchCommandResult<CanvasViolation> undoBatch( final AbstractCanvasHandler context ) {
        return stackCommandManager.undoBatch( context );
    }

    @Override
    public Collection<Command<AbstractCanvasHandler, CanvasViolation>> getBatchCommands() {
        return stackCommandManager.getBatchCommands();
    }

    @Override
    public CommandResult<CanvasViolation> allow( final AbstractCanvasHandler context,
                                                 final Command<AbstractCanvasHandler, CanvasViolation> command ) {
        return stackCommandManager.allow( context, command );
    }

    @Override
    public CommandResult<CanvasViolation> execute( AbstractCanvasHandler context, Command<AbstractCanvasHandler, CanvasViolation> command ) {
        return stackCommandManager.execute( context, command );
    }

    @Override
    public CommandResult<CanvasViolation> undo( final AbstractCanvasHandler context,
                                                final Command<AbstractCanvasHandler, CanvasViolation> command ) {
        return stackCommandManager.undo( context, command );
    }

    @Override
    public CommandResult<CanvasViolation> undo( AbstractCanvasHandler context ) {
        return stackCommandManager.undo( context );
    }

}
