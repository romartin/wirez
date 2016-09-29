package org.kie.workbench.common.stunner.core.command;

import org.kie.workbench.common.stunner.core.command.batch.BatchCommandManager;
import org.kie.workbench.common.stunner.core.command.stack.StackCommandManager;

public interface CommandManagerFactory {

    <C, V> CommandManager<C, V> newCommandManager();

    <C, V> BatchCommandManager<C, V> newBatchCommandManager();

    <C, V> StackCommandManager<C, V> newStackCommandManagerFor( BatchCommandManager<C, V> commandManager );

}
