package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.GraphCommandFactoryImpl;
import org.wirez.core.api.graph.command.impl.AddNodeCommand;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.client.canvas.command.CanvasCommandFactory;
import org.wirez.core.client.canvas.command.HasGraphCommand;
import org.wirez.core.client.factory.ShapeFactory;

public class AddCanvasNodeCommand extends AddCanvasElementCommand<Node> {

    public AddCanvasNodeCommand(final CanvasCommandFactory canvasCommandFactory, final Node candidate, final ShapeFactory factory) {
        super(canvasCommandFactory, candidate, factory);
    }

}
