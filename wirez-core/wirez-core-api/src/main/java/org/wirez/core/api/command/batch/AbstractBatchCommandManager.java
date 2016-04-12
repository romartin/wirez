package org.wirez.core.api.command.batch;

import org.uberfire.commons.validation.PortablePreconditions;
import org.wirez.core.api.command.AbstractCommandManager;
import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.command.CommandResultBuilder;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public abstract class AbstractBatchCommandManager<T, V> extends AbstractCommandManager<T, V> implements BatchCommandManager<T, V> {
    
    protected final List<Command<T,V>> commands = new LinkedList<>();
    protected final Stack<Command<T,V>> undoCommands = new Stack<>();
    
    protected abstract CommandResultBuilder<V> buildCommandResultBuilder();

    @Override
    public CommandResult<V> execute(final T context, final Command<T, V> command) {
        commands.clear();
        return super.execute(context, command);
    }

    @Override
    public BatchCommandManager<T, V> batch( final Command<T, V> command ) {
        PortablePreconditions.checkNotNull( "command", command );
        this.commands.add( command );
        return this;
    }

    @Override
    public BatchCommandResult<V> executeBatch( final T context ) {

        final BatchCommandResultBuilder<V> builder = new BatchCommandResultBuilder<V>();

        if ( !commands.isEmpty() ) {
            
            final Stack<Command<T, V>> executedCommands = new Stack<>(); 
            for (final Command<T, V> command : commands) {
                
                // Execute command.
                final CommandResult<V> result = doExecute( context, command );
                builder.add(result);
                
                // Check results.
                if ( CommandResult.Type.ERROR.equals( result.getType() ) ) {

                    // Undo previous executed commands on inverse order, so using an stack.
                    _undo( context, executedCommands );;
                    commands.clear();

                    return new BatchCommandResultBuilder<V>().add( result ).build();
                    
                } else {
                    
                    executedCommands.push( command );
                    
                }
            }

            clearCommands();
            
        }

        return builder.build();

    }
    
    protected void clearCommands() {
        super.command = null;
        undoCommands.clear();
        final Iterator<Command<T, V>> it = commands.iterator();
        while( it.hasNext() ) {
            final Command<T, V> c = it.next();
            undoCommands.push( c );
        }
        commands.clear();
    }

    @Override
    public CommandResult<V> undo(T context) {
        if ( !undoCommands.isEmpty() ) {
            CommandResult<V> result = _undo( context, undoCommands );;
            undoCommands.clear();
            return result;
        }
        
        return super.undo( context );
    }

    protected CommandResult<V> _undo(final T context, final Stack<Command<T, V>> commandStack) {

        final BatchCommandResultBuilder<V> builder = new BatchCommandResultBuilder<V>();


        if (!commandStack.isEmpty()) {

            final int size = commandStack.size();
            for ( int x = 0; x < size; x++) {
                final Command<T, V> undoCommand  = commandStack.pop();
                final CommandResult<V> undoResult = doUndo( context,  undoCommand );
                builder.add( undoResult );
            }

        }
        
        return builder.asResult( buildCommandResultBuilder() );
    }

    @Override
    protected CommandResult<V> doUndo(T context, Command<T, V> command) {
        
        // Undo the command.
        final CommandResult<V> undoResult = super.doUndo(context, command);
        
        // Check whether if undo has failed as well.
        checkUndoResult( undoResult );
        
        return undoResult;
    }

    protected void checkUndoResult(final CommandResult<V> undoResult ) {
        
        // Check whether if undo has failed as well.
        if ( CommandResult.Type.ERROR.equals( undoResult.getType() ) ) {
            throw new RuntimeException("Undo operation failed . " +
                    "The model could be inconsistent and should be sync again from previous snapshot.");
        }
        
    }



}
