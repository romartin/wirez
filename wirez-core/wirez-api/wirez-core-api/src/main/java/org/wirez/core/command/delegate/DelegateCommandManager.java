package org.wirez.core.command.delegate;

import org.wirez.core.command.*;

public abstract class DelegateCommandManager<C, V> implements CommandManager<C, V> {

    private final CommandManagerListener<C, V> listener = new CommandManagerListener<C, V>() {
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
        public void onUndo( final C context,
                            final Command<C, V> command,
                            final CommandResult<V> result ) {

            postUndo( context, command, result );

        }
    };

    protected abstract CommandManager<C, V> getDelegate(); 
    
    @Override
    public CommandResult<V> allow(final C context, 
                                  final Command<C, V> command) {

        if ( null != getDelegate() ) {
            return getDelegateWithListener().allow( context, command );
        }

        return null;
    }

    protected void postAllow( final C context,
                              final Command<C, V> command,
                              final CommandResult<V> result ) {

    }

    @Override
    public CommandResult<V> execute(final C context, 
                                    final Command<C, V> command) {

        if ( null != getDelegate() ) {
            return getDelegateWithListener().execute( context, command );
        }

        return null;
    }

    protected void postExecute( final C context,
                                final Command<C, V> command,
                                final CommandResult<V> result ) {

    }

    @Override
    public CommandResult<V> undo(final C context,
                                 final Command<C, V> command) {

        if ( null != getDelegate() ) {
            return getDelegateWithListener().undo( context, command );
        }

        return null;
    }

    protected void postUndo( final C context,
                             final Command<C, V> command,
                             final CommandResult<V> result ) {

    }

    protected CommandManagerListener<C, V> getListener() {
        return listener;
    }

    @SuppressWarnings( "unchecked" )
    private CommandManager<C, V> getDelegateWithListener() {

        final CommandManager<C, V> delegate = getDelegate();

        if ( null != delegate
                && delegate instanceof HasCommandManagerListener ) {

            ( ( HasCommandManagerListener ) delegate ).setCommandManagerListener( getListener() );
        }

        return delegate;
    }

}
