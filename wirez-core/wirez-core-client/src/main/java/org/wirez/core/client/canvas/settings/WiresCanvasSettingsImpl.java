package org.wirez.core.client.canvas.settings;

import org.wirez.core.api.command.CommandManager;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.command.CanvasCommandViolation;

public class WiresCanvasSettingsImpl extends CanvasViewSettingsImpl implements WiresCanvasSettings {

    private CommandManager<? extends CanvasHandler, CanvasCommandViolation> commandManager;

    public WiresCanvasSettingsImpl() {
    }

    public WiresCanvasSettingsImpl(final CommandManager<? extends CanvasHandler, CanvasCommandViolation> commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public CommandManager<? extends CanvasHandler, CanvasCommandViolation> getCommandManager() {
        return commandManager;
    }

    public void setCommandManager(CommandManager<? extends CanvasHandler, CanvasCommandViolation> commandManager) {
        this.commandManager = commandManager;
    }
}
