package org.kie.workbench.common.stunner.core.client.command;

import org.kie.workbench.common.stunner.core.command.Command;
import org.kie.workbench.common.stunner.core.graph.command.GraphCommandExecutionContext;
import org.kie.workbench.common.stunner.core.rule.RuleViolation;
import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;

public interface HasGraphCommand<H extends CanvasHandler> {
    
    Command<GraphCommandExecutionContext, RuleViolation> getGraphCommand(H context);
    
}
