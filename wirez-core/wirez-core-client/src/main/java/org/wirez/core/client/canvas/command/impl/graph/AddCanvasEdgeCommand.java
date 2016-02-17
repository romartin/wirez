package org.wirez.core.client.canvas.command.impl.graph;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.GraphCommandFactory;
import org.wirez.core.api.graph.command.GraphCommandFactoryImpl;
import org.wirez.core.api.graph.command.impl.AddEdgeCommand;
import org.wirez.core.api.graph.command.impl.AddNodeCommand;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.client.canvas.command.CanvasCommandFactory;
import org.wirez.core.client.canvas.command.HasGraphCommand;
import org.wirez.core.client.factory.ShapeFactory;

public class AddCanvasEdgeCommand extends org.wirez.core.client.canvas.command.impl.AddCanvasEdgeCommand implements HasGraphCommand {

    private GraphCommandFactory graphCommandFactory = new GraphCommandFactoryImpl();

    Node parent;
    
    public AddCanvasEdgeCommand(final CanvasCommandFactory canvasCommandFactory, final Node parent, final Edge candidate, final ShapeFactory factory) {
        super(canvasCommandFactory, candidate, factory);
        this.parent = parent;
    }

    @Override
    public Command<RuleManager, RuleViolation> getGraphCommand() {
        return graphCommandFactory.addEdgeCommand(parent, candidate);
    }
    
}
