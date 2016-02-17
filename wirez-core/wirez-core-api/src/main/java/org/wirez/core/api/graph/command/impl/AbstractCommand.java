package org.wirez.core.api.graph.command.impl;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.graph.command.GraphCommandFactory;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;

public abstract class AbstractCommand implements Command<RuleManager, RuleViolation> {
    
    protected GraphCommandFactory commandFactory;

    public AbstractCommand(GraphCommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }
    
    
}
