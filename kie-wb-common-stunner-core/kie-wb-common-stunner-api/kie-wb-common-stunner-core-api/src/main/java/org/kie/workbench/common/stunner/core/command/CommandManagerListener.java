package org.kie.workbench.common.stunner.core.command;

public interface CommandManagerListener<T, V> {

    void onAllow( T context, Command<T, V> command, CommandResult<V> result );

    void onExecute( T context, Command<T, V> command, CommandResult<V> result );

    void onUndo( T context, Command<T, V> command, CommandResult<V> result );


}
