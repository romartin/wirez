package org.wirez.core.client.canvas.command;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.graph.command.GraphCommandExecutionContext;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.client.canvas.CanvasHandler;

public interface HasGraphCommand<H extends CanvasHandler> {
    
    Command<GraphCommandExecutionContext, RuleViolation> getGraphCommand(H context);
    
}
