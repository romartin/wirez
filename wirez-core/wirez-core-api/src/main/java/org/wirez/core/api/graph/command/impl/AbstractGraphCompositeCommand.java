package org.wirez.core.api.graph.command.impl;

import org.wirez.core.api.command.AbstractCompositeCommand;
import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;
import org.wirez.core.api.graph.command.GraphCommandResult;
import org.wirez.core.api.rule.EmptyRuleManager;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;

public abstract class AbstractGraphCompositeCommand extends AbstractCompositeCommand<RuleManager, RuleViolation> {

    protected GraphCommandFactory commandFactory;
    protected EmptyRuleManager emptyRuleManager;

    public AbstractGraphCompositeCommand(final GraphCommandFactory commandFactory) {
        this.commandFactory = commandFactory;
        // TODO: CDI
        this.emptyRuleManager = new EmptyRuleManager();
    }

    @Override
    protected CommandResult<RuleViolation> doUndo(final RuleManager context, 
                                                  final Command<RuleManager, RuleViolation> command) {
        return command.undo( emptyRuleManager );
    }

    @Override
    protected CommandResult<RuleViolation> doExecute(final RuleManager context, 
                                                     final Command<RuleManager, RuleViolation> command) {
        return command.execute( emptyRuleManager );
    }

    @Override
    protected CommandResult<RuleViolation> buildResult() {
        return new GraphCommandResult();
    }
}
