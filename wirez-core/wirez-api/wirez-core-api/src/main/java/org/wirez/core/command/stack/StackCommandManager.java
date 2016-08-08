package org.wirez.core.command.stack;

import org.wirez.core.command.Command;
import org.wirez.core.command.CommandResult;
import org.wirez.core.command.batch.BatchCommandManager;
import org.wirez.core.registry.command.CommandRegistry;

import java.util.Stack;

public interface StackCommandManager<T, V> extends BatchCommandManager<T, V> {

    CommandRegistry<Command<T, V>> getRegistry();

    /**
     * Undo latest command in the registry..
     */
    CommandResult<V> undo( T context );

}
