package org.wirez.core.command.batch;

import org.wirez.core.command.Command;
import org.wirez.core.command.CommandManagerListener;
import org.wirez.core.command.CommandResult;

import java.util.Collection;

public interface BatchCommandManagerListener<T, V> extends CommandManagerListener<T, V> {

    void onExecuteBatch( T context, Collection<Command<T, V>> commands, BatchCommandResult<V> result );

    void onUndoBatch( T context, Collection<Command<T, V>> commands, CommandResult<V> result );

}
