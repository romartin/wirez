package org.wirez.core.client.canvas.command;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.CanvasHandler;

public interface HasGraphCommand<H extends CanvasHandler, F> {
    
    
    Command<RuleManager, RuleViolation> getGraphCommand(H canvasHandler, F factory);
    
}
