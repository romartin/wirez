package org.wirez.core.client.command;

import org.wirez.core.command.Command;
import org.wirez.core.graph.command.GraphCommandExecutionContext;
import org.wirez.core.rule.RuleViolation;
import org.wirez.core.client.canvas.CanvasHandler;

public interface HasGraphCommand<H extends CanvasHandler> {
    
    Command<GraphCommandExecutionContext, RuleViolation> getGraphCommand(H context);
    
}
