package org.wirez.core.client.canvas.command.impl.graph;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.GraphCommandFactory;
import org.wirez.core.api.graph.command.GraphCommandFactoryImpl;
import org.wirez.core.api.graph.command.impl.DeleteEdgeCommand;
import org.wirez.core.api.graph.command.impl.DeleteNodeCommand;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.client.canvas.command.CanvasCommandFactory;
import org.wirez.core.client.canvas.command.HasGraphCommand;
import org.wirez.core.client.factory.ShapeFactory;

public class DeleteCanvasEdgeCommand extends org.wirez.core.client.canvas.command.impl.DeleteCanvasEdgeCommand implements HasGraphCommand {

    private GraphCommandFactory graphCommandFactory = new GraphCommandFactoryImpl();

    public DeleteCanvasEdgeCommand(final CanvasCommandFactory canvasCommandFactory, final Edge candidate, final ShapeFactory factory) {
        super(canvasCommandFactory, candidate, factory);
    }

    @Override
    public Command<RuleManager, RuleViolation> getGraphCommand() {
        return graphCommandFactory.deleteEdgeCommand(candidate);
    }
    
}
