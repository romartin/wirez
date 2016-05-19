package org.wirez.core.command.delegate;

import org.wirez.core.command.Command;
import org.wirez.core.command.CommandManager;
import org.wirez.core.command.CommandResult;

public abstract class DelegateCommandManager<C, V> implements CommandManager<C, V> {
    
    protected abstract CommandManager<C, V> getDelegate(); 
    
    @Override
    public CommandResult<V> allow(final C context, 
                                  final Command<C, V> command) {
        if ( null != getDelegate() ) {
            return getDelegate().allow( context, command );
        }
        
        return null;
    }

    @Override
    public CommandResult<V> execute(final C context, 
                                    final Command<C, V> command) {

        if ( null != getDelegate() ) {
            return getDelegate().execute( context, command );
        }
        
        return null;
    }

    @Override
    public CommandResult<V> undo(final C context) {

        if ( null != getDelegate() ) {
            return getDelegate().undo( context );
        }
        
        return null;
    }
}
