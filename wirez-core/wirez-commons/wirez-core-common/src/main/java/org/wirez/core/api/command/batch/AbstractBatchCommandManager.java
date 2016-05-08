package org.wirez.core.api.command.batch;

import org.uberfire.commons.validation.PortablePreconditions;
import org.wirez.core.api.command.*;

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
        
        if ( !commands.isEmpty() ) {
            throw new RuntimeException(" Cannot execute a command while there exist batch commands already queued.");
        }
        
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
                    _undoMultipleCommands( context, executedCommands );;
                    clean();

                    return new BatchCommandResultBuilder<V>().add( result ).build();
                    
                } else {
                    
                    executedCommands.push( command );
                    
                }
            }

            cleanKeepHistory();
            
        }

        return builder.build();

    }
    
    protected void cleanKeepHistory() {
        super.command = null;
        undoCommands.clear();
        final Iterator<Command<T, V>> it = commands.iterator();
        while( it.hasNext() ) {
            final Command<T, V> c = it.next();
            undoCommands.push( c );
        }
        commands.clear();
    }

    protected void clean() {
        super.command = null;
        undoCommands.clear();
        commands.clear();
    }

    @Override
    public CommandResult<V> undo(T context) {
        final CommandResult<V> parentUndoResult = super.undo( context );
        
        if ( !CommandUtils.isError(parentUndoResult) && !undoCommands.isEmpty() ) {
            CommandResult<V> result = _undoMultipleCommands( context, undoCommands );;
            clean();
            return result;
        }
        
        return parentUndoResult;
    }

    // Undo the standalone command executed on the parent.
    @Override
    protected CommandResult<V> doUndo( final T context, 
                                       final Command<T, V> command) {

        // Undo the command.
        final CommandResult<V> undoResult = super.doUndo(context, command);

        // Check whether if undo has failed as well.
        checkUndoResult( undoResult );

        return undoResult;
    }

    // Undo the batched commands for last single execution.
    protected CommandResult<V> _undoMultipleCommands(final T context, 
                                                     final Stack<Command<T, V>> commandStack) {

        final BatchCommandResultBuilder<V> builder = new BatchCommandResultBuilder<V>();

        while ( !commandStack.isEmpty() ) {
            final Command<T, V> undoCommand  = commandStack.pop();
            final CommandResult<V> undoResult = doUndo( context,  undoCommand );
            if ( null != undoResult ) {
                builder.add( undoResult );
            }
        }

        
        return builder.asResult( buildCommandResultBuilder() );
    }

    @Override
    protected boolean hasUndoCommand() {
        return super.hasUndoCommand() || !commands.isEmpty();
    }

    protected void checkUndoResult(final CommandResult<V> undoResult ) {
        
        // Check whether if undo has failed as well.
        if ( null != undoResult && CommandResult.Type.ERROR.equals( undoResult.getType() ) ) {
            throw new RuntimeException("Undo operation failed . " +
                    "The model could be inconsistent and should be sync again from previous snapshot.");
        }
        
    }



}
