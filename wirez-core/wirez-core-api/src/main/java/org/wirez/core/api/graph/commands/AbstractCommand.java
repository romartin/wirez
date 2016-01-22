package org.wirez.core.api.graph.commands;

import org.wirez.core.api.command.Command;

public abstract class AbstractCommand implements Command {
    
    protected GraphCommandFactory commandFactory;

    public AbstractCommand(GraphCommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }
    
    
}
