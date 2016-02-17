package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.api.graph.Node;
import org.wirez.core.client.canvas.command.CanvasCommandFactory;
import org.wirez.core.client.factory.ShapeFactory;

public class DeleteCanvasNodeCommand extends DeleteCanvasElementCommand<Node> {

    public DeleteCanvasNodeCommand(final CanvasCommandFactory canvasCommandFactory, final Node candidate, final ShapeFactory factory) {
        super(canvasCommandFactory, candidate, factory);
    }

}
