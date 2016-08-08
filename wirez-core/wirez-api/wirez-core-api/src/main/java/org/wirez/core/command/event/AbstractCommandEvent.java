package org.wirez.core.command.event;

import org.wirez.core.command.Command;
import org.wirez.core.command.CommandResult;

import java.util.ArrayList;
import java.util.Collection;

public abstract class AbstractCommandEvent<C, V> {

    private final Collection<Command<C, V>> commands;
    private final boolean isBatch;
    private final CommandResult<V> result;

    public AbstractCommandEvent( final Collection<Command<C, V>> commands,
                                 final CommandResult<V> result ) {
        this.commands = commands;
        this.isBatch = true;
        this.result = result;
    }

    public AbstractCommandEvent( final Command<C, V> command,
                                 final CommandResult<V> result ) {
        this.commands = new ArrayList<Command<C, V>>( 1 ) {{
            add( command );
        }};
        this.isBatch = false;
        this.result = result;
    }
    
    public Command<C, V> getCommand() {

        if ( !isBatch ) {

            return commands.iterator().next();
        }

        return null;
    }

    public Collection<Command<C, V>> getCommands() {

        if ( isBatch ) {

            return commands;

        }

        return null;
    }

    public CommandResult<V> getResult() {
        return result;
    }

    public boolean hasError() {
        return result != null && result.getType() != null && CommandResult.Type.ERROR.equals( result.getType() );
    }
}
