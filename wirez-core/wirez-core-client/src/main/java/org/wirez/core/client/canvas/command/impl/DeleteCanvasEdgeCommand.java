package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.client.canvas.command.CanvasCommandFactory;
import org.wirez.core.client.factory.ShapeFactory;

public class DeleteCanvasEdgeCommand extends DeleteCanvasElementCommand<Edge> {
    
    public DeleteCanvasEdgeCommand(final CanvasCommandFactory canvasCommandFactory, Edge candidate, ShapeFactory factory) {
        super(candidate, factory);
    }
    
}
