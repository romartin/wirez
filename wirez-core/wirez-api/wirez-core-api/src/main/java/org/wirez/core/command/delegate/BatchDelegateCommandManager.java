package org.wirez.core.command.delegate;

import org.wirez.core.command.*;
import org.wirez.core.command.batch.BatchCommandManager;
import org.wirez.core.command.batch.BatchCommandManagerListener;
import org.wirez.core.command.batch.BatchCommandResult;

import java.util.Collection;

public abstract class BatchDelegateCommandManager<C, V> extends DelegateCommandManager<C, V> 
    implements BatchCommandManager<C,V> {

    private final BatchCommandManagerListener<C, V> listener = new BatchCommandManagerListener<C, V>() {

        @Override
        public void onAllow( final C context,
                             final Command<C, V> command,
                             final CommandResult<V> result ) {

            postAllow( context, command, result );

        }

        @Override
        public void onExecute( final C context,
                               final Command<C, V> command,
                               final CommandResult<V> result ) {

            postExecute( context, command, result );

        }

        @Override
        public void onExecuteBatch( final C context,
                                    final Collection<Command<C, V>> commands,
                                    final BatchCommandResult<V> result ) {

            postExecuteBatch( context, commands, result );

        }

        @Override
        public void onUndoBatch( final C context,
                                 final Collection<Command<C, V>> commands,
                                 final CommandResult<V> result ) {

            postUndo( context, commands, result );

        }

        @Override
        public void onUndo( final C context,
                            final Command<C, V> command,
                            final CommandResult<V> result ) {

            postUndo( context, command, result );

        }

    };

    protected abstract BatchCommandManager<C, V> getBatchDelegate();

    @Override
    protected CommandManager<C, V> getDelegate() {
        return getBatchDelegate();
    }

    @Override
    protected CommandManagerListener<C, V> getListener() {
        return listener;
    }

    @Override
    public Collection<Command<C, V>> getBatchCommands() {
        return getBatchDelegate().getBatchCommands();
    }

    @Override
    public BatchCommandManager<C, V> batch( final Command<C, V> command) {

        if ( null != getBatchDelegate() ) {
            return getBatchDelegateWithListener().batch( command );
        }
        
        return null;
    }

    @Override
    public BatchCommandResult<V> executeBatch(final C context) {

        BatchCommandResult<V> result = null;

        if ( null != getBatchDelegate() ) {
            result = getBatchDelegateWithListener().executeBatch( context );
        }

        return result;
    }

    @Override
    public BatchCommandResult<V> undoBatch( final C context ) {

        BatchCommandResult<V> result = null;

        if ( null != getBatchDelegate() ) {
            result = getBatchDelegateWithListener().undoBatch( context );
        }

        return result;

    }



    protected void postExecuteBatch( final C context,
                                     final Collection<Command<C, V>> commands,
                                     final BatchCommandResult<V> result ) {

    }

    protected void postUndo( final C context,
                             final Collection<Command<C, V>> commands,
                             final CommandResult<V> result ) {

    }

    @SuppressWarnings( "unchecked" )
    private BatchCommandManager<C, V> getBatchDelegateWithListener() {

        final BatchCommandManager<C, V> delegate = getBatchDelegate();

        if ( null != delegate
                && delegate instanceof HasCommandManagerListener ) {

            ( ( HasCommandManagerListener ) delegate ).setCommandManagerListener( getListener() );
        }

        return delegate;
    }
   
}
