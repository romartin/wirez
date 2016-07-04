package org.wirez.core.client.canvas.controls.toolbox.command;

import org.wirez.core.client.canvas.controls.toolbox.command.builder.NewConnectorCommand;
import org.wirez.core.client.canvas.controls.toolbox.command.builder.NewNodeCommand;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
public class DefaultToolboxCommandFactory {

    Instance<NewNodeCommand> newNodeCommands;
    Instance<NewConnectorCommand> newConnectorCommands;

    protected DefaultToolboxCommandFactory() {
        this( null, null );
    }

    @Inject
    public DefaultToolboxCommandFactory( final Instance<NewNodeCommand> newNodeCommands,
                                         final Instance<NewConnectorCommand> newConnectorCommands) {
        this.newNodeCommands = newNodeCommands;
        this.newConnectorCommands = newConnectorCommands;
    }

    public NewNodeCommand newNodeCommand() {
        return newNodeCommands.get();
    }

    public NewConnectorCommand newConnectorCommand() {
        return newConnectorCommands.get();
    }

}
