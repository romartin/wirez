package org.wirez.core.api.event.command;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;

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
