package org.wirez.core.command;

import org.wirez.core.command.batch.BatchCommandManager;
import org.wirez.core.command.stack.StackCommandManager;

public interface CommandManagerFactory {

    <C, V> CommandManager<C, V> newCommandManager();

    <C, V> BatchCommandManager<C, V> newBatchCommandManager();

    <C, V> StackCommandManager<C, V> newStackCommandManagerFor( BatchCommandManager<C, V> commandManager );

}
