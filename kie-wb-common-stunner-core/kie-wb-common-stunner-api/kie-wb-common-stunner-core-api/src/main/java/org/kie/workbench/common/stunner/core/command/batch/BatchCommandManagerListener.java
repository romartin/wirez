package org.kie.workbench.common.stunner.core.command.batch;

import org.kie.workbench.common.stunner.core.command.CommandManagerListener;
import org.kie.workbench.common.stunner.core.command.CommandResult;
import org.kie.workbench.common.stunner.core.command.Command;

import java.util.Collection;

public interface BatchCommandManagerListener<T, V> extends CommandManagerListener<T, V> {

    void onExecuteBatch( T context, Collection<Command<T, V>> commands, BatchCommandResult<V> result );

    void onUndoBatch( T context, Collection<Command<T, V>> commands, CommandResult<V> result );

}
