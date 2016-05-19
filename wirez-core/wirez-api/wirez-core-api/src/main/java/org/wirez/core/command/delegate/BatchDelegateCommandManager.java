package org.wirez.core.command.delegate;

import org.wirez.core.command.Command;
import org.wirez.core.command.CommandManager;
import org.wirez.core.command.batch.BatchCommandManager;
import org.wirez.core.command.batch.BatchCommandResult;

public abstract class BatchDelegateCommandManager<C, V> extends DelegateCommandManager<C, V> 
    implements BatchCommandManager<C,V> {

    protected abstract BatchCommandManager<C, V> getBatchDelegate();

    @Override
    protected CommandManager<C, V> getDelegate() {
        return getBatchDelegate();
    }

    @Override
    public BatchCommandManager<C, V> batch(Command<C, V> command) {

        if ( null != getBatchDelegate() ) {
            return getBatchDelegate().batch( command );
        }
        
        return null;
    }

    @Override
    public BatchCommandResult<V> executeBatch(C context) {

        if ( null != getBatchDelegate() ) {
            return getBatchDelegate().executeBatch( context );
        }
        
        return null;
    }

   
}
