package org.wirez.core.client.canvas.settings;

import org.wirez.core.api.command.CommandManager;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.command.CanvasCommandViolation;
import org.wirez.core.client.canvas.control.ConnectionAcceptor;
import org.wirez.core.client.canvas.control.ContainmentAcceptor;
import org.wirez.core.client.canvas.impl.WiresCanvasHandler;

public class WiresCanvasSettingsImpl extends CanvasViewSettingsImpl implements WiresCanvasSettings {

    private CommandManager<WiresCanvasHandler, CanvasCommandViolation> commandManager;
    private RuleManager ruleManager;
    private ConnectionAcceptor connectionAcceptor;
    private ContainmentAcceptor containmentAcceptor;
    
    public WiresCanvasSettingsImpl() {
    }

    @Override
    public CommandManager<WiresCanvasHandler, CanvasCommandViolation> getCommandManager() {
        return commandManager;
    }

    @Override
    public RuleManager getRuleManager() {
        return ruleManager;
    }

    @Override
    public ConnectionAcceptor getConnectionAcceptor() {
        return connectionAcceptor;
    }

    @Override
    public ContainmentAcceptor getContainmentAcceptor() {
        return containmentAcceptor;
    }

    public void setCommandManager(CommandManager<WiresCanvasHandler, CanvasCommandViolation> commandManager) {
        this.commandManager = commandManager;
    }

    public void setRuleManager(RuleManager ruleManager) {
        this.ruleManager = ruleManager;
    }

    public void setConnectionAcceptor(ConnectionAcceptor connectionAcceptor) {
        this.connectionAcceptor = connectionAcceptor;
    }

    public void setContainmentAcceptor(ContainmentAcceptor containmentAcceptor) {
        this.containmentAcceptor = containmentAcceptor;
    }
}
