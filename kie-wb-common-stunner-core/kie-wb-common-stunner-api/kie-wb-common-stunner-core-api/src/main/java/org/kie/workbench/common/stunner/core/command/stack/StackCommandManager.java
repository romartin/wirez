package org.kie.workbench.common.stunner.core.command.stack;

import org.kie.workbench.common.stunner.core.command.Command;
import org.kie.workbench.common.stunner.core.command.CommandResult;
import org.kie.workbench.common.stunner.core.command.batch.BatchCommandManager;
import org.kie.workbench.common.stunner.core.registry.command.CommandRegistry;

public interface StackCommandManager<T, V> extends BatchCommandManager<T, V> {

    CommandRegistry<Command<T, V>> getRegistry();

    /**
     * Undo latest command in the registry..
     */
    CommandResult<V> undo( T context );

}
