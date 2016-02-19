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
import org.wirez.core.client.factory.ShapeFactory;

public class AddCanvasNodeCommand extends AddCanvasElementCommand<Node> implements HasGraphCommand<WiresCanvasHandler, GraphCommandFactory> {

    public AddCanvasNodeCommand(final CanvasCommandFactory canvasCommandFactory, final Node candidate, final ShapeFactory factory) {
        super(canvasCommandFactory, candidate, factory);
    }

    @Override
    public Command<RuleManager, RuleViolation> getGraphCommand(WiresCanvasHandler canvasHandler, GraphCommandFactory factory) {
        return factory.ADD_NOE(canvasHandler.getDiagram().getGraph(), candidate);
    }

    @Override
    public CommandResult<CanvasCommandViolation> undo(WiresCanvasHandler context) {
        return canvasCommandFactory.DELETE_NODE(candidate).execute( context );
    }
}
