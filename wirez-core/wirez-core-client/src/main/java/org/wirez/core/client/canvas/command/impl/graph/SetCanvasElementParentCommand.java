package org.wirez.core.client.canvas.command.impl.graph;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.GraphCommandFactory;
import org.wirez.core.api.graph.command.GraphCommandFactoryImpl;
import org.wirez.core.api.graph.content.ParentChildRelationship;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.client.canvas.command.CanvasCommandFactory;
import org.wirez.core.client.canvas.command.HasGraphCommand;

public class SetCanvasElementParentCommand extends org.wirez.core.client.canvas.command.impl.SetCanvasElementParentCommand implements HasGraphCommand {

    private GraphCommandFactory graphCommandFactory = new GraphCommandFactoryImpl();

    Edge<ParentChildRelationship, Node> edge;
    
    public SetCanvasElementParentCommand(final CanvasCommandFactory canvasCommandFactory,
                                         final Node parent,
                                         final Node candidate,
                                         final Edge<ParentChildRelationship, Node> edge) {
        super(canvasCommandFactory, parent, candidate);
        this.edge = edge;
    }

    @Override
    public Command<RuleManager, RuleViolation> getGraphCommand() {
        return graphCommandFactory.setParentCommand(parent, candidate, edge);
    }
    
}
