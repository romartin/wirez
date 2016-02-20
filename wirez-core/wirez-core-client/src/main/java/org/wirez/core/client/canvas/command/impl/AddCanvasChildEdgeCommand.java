package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.client.canvas.command.CanvasCommandViolation;
import org.wirez.core.client.canvas.command.HasGraphCommand;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.impl.WiresCanvasHandler;

/**
 * TODO: Register the new edge into the canvas handler's index for the graph. 
 */
public class AddCanvasChildEdgeCommand extends AbstractCanvasCommand implements HasGraphCommand<WiresCanvasHandler, GraphCommandFactory> {

    protected Node parent;
    protected Node candidate;

    public AddCanvasChildEdgeCommand(final CanvasCommandFactory canvasCommandFactory,
                                     final Node parent,
                                     final Node candidate) {
        super(canvasCommandFactory);
        this.parent = parent;
        this.candidate = candidate;
    }

    @Override
    public CommandResult<CanvasCommandViolation> execute(final WiresCanvasHandler context) {
        context.addChild(parent, candidate);
        context.applyElementMutation(candidate);
        return buildResult();
    }

    @Override
    public CommandResult<CanvasCommandViolation> undo(final WiresCanvasHandler context) {
        // TODO
        return null;
    }

    @Override
    public Command<RuleManager, RuleViolation> getGraphCommand(WiresCanvasHandler canvasHandler, GraphCommandFactory factory) {
        return factory.ADD_CHILD_EDGE(parent, candidate);
    }
    
}
