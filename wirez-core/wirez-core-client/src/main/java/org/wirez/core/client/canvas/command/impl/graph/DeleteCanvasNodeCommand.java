package org.wirez.core.client.canvas.command.impl.graph;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.GraphCommandFactory;
import org.wirez.core.api.graph.command.GraphCommandFactoryImpl;
import org.wirez.core.api.graph.command.impl.AddNodeCommand;
import org.wirez.core.api.graph.command.impl.DeleteNodeCommand;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.client.canvas.command.CanvasCommandFactory;
import org.wirez.core.client.canvas.command.HasGraphCommand;
import org.wirez.core.client.factory.ShapeFactory;

public class DeleteCanvasNodeCommand extends org.wirez.core.client.canvas.command.impl.DeleteCanvasNodeCommand implements HasGraphCommand {

    private GraphCommandFactory graphCommandFactory = new GraphCommandFactoryImpl();

    Graph graph;
    
    public DeleteCanvasNodeCommand(final CanvasCommandFactory canvasCommandFactory, final Graph graph, final Node candidate, final ShapeFactory factory) {
        super(canvasCommandFactory, candidate, factory);
        this.graph = graph;
    }

    @Override
    public Command<RuleManager, RuleViolation> getGraphCommand() {
        return graphCommandFactory.deleteNodeCommand(graph, candidate);
    }
    
}
