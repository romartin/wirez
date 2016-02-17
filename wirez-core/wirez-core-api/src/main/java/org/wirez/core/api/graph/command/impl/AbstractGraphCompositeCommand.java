package org.wirez.core.api.graph.command.impl;

import org.wirez.core.api.command.AbstractCompositeCommand;
import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.command.GraphCommandFactory;
import org.wirez.core.api.graph.command.GraphCommandResult;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractGraphCompositeCommand extends AbstractCompositeCommand<RuleManager, RuleViolation> {

    protected GraphCommandFactory commandFactory;

    public AbstractGraphCompositeCommand(GraphCommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    @Override
    protected CommandResult<RuleViolation> buildResult() {
        return new GraphCommandResult();
    }
}
