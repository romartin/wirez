package org.wirez.core.client.canvas.command;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;

public interface HasGraphCommand {
    
    Command<RuleManager, RuleViolation> getGraphCommand();
    
}
