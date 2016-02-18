package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.client.canvas.command.HasGraphCommand;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.command.CanvasCommandViolation;
import org.wirez.core.client.canvas.impl.WiresCanvasHandler;
import org.wirez.core.client.factory.ShapeFactory;
import org.wirez.core.client.impl.BaseConnector;

public class AddCanvasEdgeCommand extends AddCanvasElementCommand<Edge> implements HasGraphCommand<WiresCanvasHandler, GraphCommandFactory>  {
    
    Node parent;
    
    public AddCanvasEdgeCommand(final CanvasCommandFactory canvasCommandFactory, final Node parent, final Edge candidate, final ShapeFactory factory) {
        super(canvasCommandFactory, candidate, factory);
        this.parent = parent;
    }

    @Override
    public CommandResult<CanvasCommandViolation> execute(final WiresCanvasHandler context) {
        CommandResult<CanvasCommandViolation> result = super.execute(context);
        final String uuid = candidate.getUUID();
        BaseConnector connector = (BaseConnector) context.getCanvas().getShape(uuid);
        connector.applyConnections((Edge<ViewContent, Node>) candidate, context);
        return result;
    }


    @Override
    public Command<RuleManager, RuleViolation> getGraphCommand(WiresCanvasHandler canvasHandler, GraphCommandFactory factory) {
        return factory.ADD_EDGE(parent, candidate);
    }
    
}
