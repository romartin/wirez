package org.wirez.core.api.graph.command.impl;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;

public abstract class AbstractGraphCommand implements Command<RuleManager, RuleViolation> {
    
    protected GraphCommandFactory commandFactory;

    public AbstractGraphCommand(GraphCommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }
    
    
}
