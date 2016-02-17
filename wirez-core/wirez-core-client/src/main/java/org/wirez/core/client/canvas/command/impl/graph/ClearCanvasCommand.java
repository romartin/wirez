package org.wirez.core.client.canvas.command.impl.graph;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.GraphCommandFactory;
import org.wirez.core.api.graph.command.GraphCommandFactoryImpl;
import org.wirez.core.api.graph.command.impl.AddEdgeCommand;
import org.wirez.core.api.graph.command.impl.ClearGraphCommand;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.client.canvas.command.CanvasCommandFactory;
import org.wirez.core.client.canvas.command.HasGraphCommand;
import org.wirez.core.client.factory.ShapeFactory;

public class ClearCanvasCommand extends org.wirez.core.client.canvas.command.impl.ClearCanvasCommand implements HasGraphCommand {

    private GraphCommandFactory graphCommandFactory = new GraphCommandFactoryImpl();

    Graph graph;
    
    public ClearCanvasCommand(final CanvasCommandFactory canvasCommandFactory, final Graph graph) {
        super(canvasCommandFactory);
        this.graph = graph;
    }

    @Override
    public Command<RuleManager, RuleViolation> getGraphCommand() {
        return graphCommandFactory.clearGraphCommand(graph);
    }
    
}
