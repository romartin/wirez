package org.wirez.core.command;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractCompositeCommand<T, V> implements CompositeCommand<T, V> {
    
    protected final List<Command<T, V>> commands = new LinkedList<>();
    private boolean initialized = false;

    public AbstractCompositeCommand<T,V> addCommand(final Command<T, V> command) {
        commands.add(command);
        return this;
    }

    protected abstract void initialize( T context );
    
    protected abstract CommandResult<V> buildResult( final List<CommandResult<V>> violations );

    protected abstract CommandResult<V> doAllow( T context, Command<T, V> command);
    
    protected abstract CommandResult<V> doExecute( T context, Command<T, V> command);

    protected abstract CommandResult<V> doUndo( T context, Command<T, V> command );

    @Override
    public CommandResult<V> allow(T context) {
        checkInitialized( context );
        final List<CommandResult<V>> results = new LinkedList<>();
        for ( final Command<T, V> command : commands ) {
            final CommandResult<V> violations = doAllow( context, command );
            results.add(violations);
        }

        return buildResult(results);
    }

    @Override
    public CommandResult<V> execute(final T context) {
        CommandResult<V> allowResult = this.allow( context );
       
        if ( ! CommandUtils.isError(allowResult) ) {
            final List<CommandResult<V>> results = new LinkedList<>();
            for ( final Command<T, V> command : commands ) {
                final CommandResult<V> violations = doExecute( context, command );
                results.add(violations);
            }

            return buildResult(results);
        }

        return allowResult;
    }
    

    @Override
    public CommandResult<V> undo(final T context) {
        final List<CommandResult<V>> results = new LinkedList<>();
        for ( final Command<T, V> command : commands ) {
            final CommandResult<V> violations = doUndo( context, command );
            results.add(violations);
        }

        return buildResult(results);
    }
    
    private void checkInitialized(T context) {
        if( !initialized ) {
            initialize( context );
            this.initialized = true;
        }
    }
    
    
}
