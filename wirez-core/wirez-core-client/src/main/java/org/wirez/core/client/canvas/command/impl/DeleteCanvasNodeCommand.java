package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;
import org.wirez.core.api.graph.content.ParentChildRelationship;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.client.canvas.command.CanvasCommandViolation;
import org.wirez.core.client.canvas.command.HasGraphCommand;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.impl.WiresCanvasHandler;
import org.wirez.core.client.factory.ShapeFactory;

import java.util.List;

public class DeleteCanvasNodeCommand extends DeleteCanvasElementCommand<Node> implements HasGraphCommand<WiresCanvasHandler, GraphCommandFactory> {

    public DeleteCanvasNodeCommand(final CanvasCommandFactory canvasCommandFactory, final Node candidate) {
        super(canvasCommandFactory, candidate);
    }

    // TODO: Support for multiple parents.
    @Override
    protected Node getParent() {
        List<Edge> inEdges = candidate.getInEdges();
        if ( null != inEdges && !inEdges.isEmpty() ) {
            for ( final Edge edge : inEdges ) {
                if ( edge.getContent() instanceof ParentChildRelationship) {
                    return edge.getSourceNode();
                }
            }
        }
        return null;
    }
    

    @Override
    public Command<RuleManager, RuleViolation> getGraphCommand(WiresCanvasHandler canvasHandler, GraphCommandFactory factory) {
        return factory.DELETE_NODE(canvasHandler.getDiagram().getGraph(), candidate);
    }

    @Override
    public CommandResult<CanvasCommandViolation> undo(WiresCanvasHandler context) {
        final Command<WiresCanvasHandler, CanvasCommandViolation> command = parent != null ?
                canvasCommandFactory.ADD_CHILD_NODE( parent, candidate, factory) : 
                canvasCommandFactory.ADD_NODE( candidate, factory);
        return command.execute( context );
    }
}
