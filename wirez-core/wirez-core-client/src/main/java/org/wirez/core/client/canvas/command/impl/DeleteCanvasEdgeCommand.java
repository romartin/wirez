package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.client.canvas.command.HasGraphCommand;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.impl.WiresCanvasHandler;
import org.wirez.core.client.factory.ShapeFactory;

public class DeleteCanvasEdgeCommand extends DeleteCanvasElementCommand<Edge> implements HasGraphCommand<WiresCanvasHandler, GraphCommandFactory> {
    
    public DeleteCanvasEdgeCommand(final CanvasCommandFactory canvasCommandFactory, Edge candidate, ShapeFactory factory) {
        super(canvasCommandFactory, candidate, factory);
    }

    @Override
    public Command<RuleManager, RuleViolation> getGraphCommand(WiresCanvasHandler canvasHandler, GraphCommandFactory factory) {
        return factory.deleteEdgeCommand(candidate);
    }
    
}
