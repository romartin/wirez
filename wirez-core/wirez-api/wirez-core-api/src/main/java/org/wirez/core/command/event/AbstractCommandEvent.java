package org.wirez.core.command.event;

import org.wirez.core.command.Command;
import org.wirez.core.command.CommandResult;

public abstract class AbstractCommandEvent<C, V> {
    
    protected final Command<C, V> command;
    protected final CommandResult<V> result;

    public AbstractCommandEvent(Command<C, V> command, CommandResult<V> result) {
        this.command = command;
        this.result = result;
    }
    
    public Command<C, V> getCommand() {
        return command;
    }

    public CommandResult<V> getResult() {
        return result;
    }

    public boolean hasError() {
        return result != null && result.getType() != null && CommandResult.Type.ERROR.equals( result.getType() );
    }
}
