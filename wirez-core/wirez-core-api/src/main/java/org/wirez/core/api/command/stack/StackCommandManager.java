package org.wirez.core.api.command.stack;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.command.CommandUtils;
import org.wirez.core.api.command.batch.AbstractBatchCommandManager;
import org.wirez.core.api.command.batch.BatchCommandResult;
import org.wirez.core.api.command.batch.BatchCommandResultBuilder;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public abstract class StackCommandManager<C, V> extends AbstractBatchCommandManager<C,V> {
    
    protected final Stack<Stack<Command<C,V>>> history = new Stack<>();
    protected int maxStackSize = 50;
    
    @Override
    public CommandResult<V> execute(C context, Command<C, V> command) {
        CommandResult<V> result = super.execute(context, command);

        final boolean isError = CommandUtils.isError( result );
        if ( !isError ) {
            addIntoStack( command );
        }

        return result;
    }

    @Override
    public BatchCommandResult<V> executeBatch(final C context) {
        final List<Command<C,V>> batchCommands = new LinkedList<>( super.commands );
        final BatchCommandResult<V> result = super.executeBatch(context);

        final boolean isError = CommandUtils.isError( result );
        if ( !isError ) {
            addIntoStack( batchCommands );
        }
        
        return result;
    }

    @Override
    public CommandResult<V> undo(C context) {

        final BatchCommandResultBuilder<V> builder = new BatchCommandResultBuilder<V>();

        if ( !history.isEmpty() ) {

            final Stack<Command<C, V>> commandStack = history.pop();
            if ( null != commandStack ) {
                CommandResult<V> result = _undo( context, commandStack );
                builder.add( result );
            }
            
            return builder.asResult( buildCommandResultBuilder() );
        }
        
        return super.undo( context );
    }
    
    protected void addIntoStack( final Command<C,V> command ) {
        if ( null != command ) {
            if ( ( history.size() + 1 )  > maxStackSize ) {
                stackSizeExceeded();
            }
            final Stack<Command<C,V>> s = new Stack<>();
            s.push( command );
            history.push( s );
        }
    }

    protected void addIntoStack( final Collection<Command<C,V>> commands ) {
        if ( null != commands && !commands.isEmpty() ) {
            if ( ( history.size() + commands.size() ) > maxStackSize ) {
                stackSizeExceeded();
            }
            final Stack<Command<C,V>> s = new Stack<>();
            for ( final Command<C, V> c : commands ) {
                s.push( c );
            }
            history.push( s );
        }
    }
    
    public int getHistorySize() {
        return history.size();
    }
    
    protected void stackSizeExceeded() {
        throw new RuntimeException("Commands stack is full.");
    }
    
}
