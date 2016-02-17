package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.client.canvas.command.CanvasCommandFactory;
import org.wirez.core.client.canvas.command.CanvasCommandViolation;
import org.wirez.core.client.canvas.impl.WiresCanvasHandler;
import org.wirez.core.client.factory.ShapeFactory;
import org.wirez.core.client.impl.BaseConnector;

public class AddCanvasEdgeCommand extends AddCanvasElementCommand<Edge> {
    
    public AddCanvasEdgeCommand(final CanvasCommandFactory canvasCommandFactory, final Edge candidate, final ShapeFactory factory) {
        super(canvasCommandFactory, candidate, factory);
    }

    @Override
    public CommandResult<CanvasCommandViolation> execute(final WiresCanvasHandler context) {
        CommandResult<CanvasCommandViolation> result = super.execute(context);
        final String uuid = candidate.getUUID();
        BaseConnector connector = (BaseConnector) context.getCanvas().getShape(uuid);
        connector.applyConnections((Edge<ViewContent, Node>) candidate, context);
        return result;
    }
    
}
