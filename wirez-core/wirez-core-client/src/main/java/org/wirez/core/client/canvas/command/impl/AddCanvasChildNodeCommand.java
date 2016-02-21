package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;
import org.wirez.core.api.graph.processing.index.IncrementalIndexBuilder;
import org.wirez.core.api.graph.processing.index.Index;
import org.wirez.core.api.graph.processing.index.IndexBuilder;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.client.canvas.command.CanvasCommandViolation;
import org.wirez.core.client.canvas.command.HasGraphCommand;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.wires.WiresCanvasHandler;
import org.wirez.core.client.factory.ShapeFactory;

/**
 * TODO: Register the new edge into the canvas hander's index for the graph. 
 */
public class AddCanvasChildNodeCommand extends AddCanvasElementCommand<Node> implements HasGraphCommand<WiresCanvasHandler, GraphCommandFactory> {

    protected Node parent;

    public AddCanvasChildNodeCommand(final CanvasCommandFactory canvasCommandFactory, final Node parent, final Node candidate, final ShapeFactory factory) {
        super(canvasCommandFactory, candidate, factory);
        this.parent = parent;
    }
    
    @Override
    protected void doRegister(WiresCanvasHandler context) {
        super.doRegister(context);
        context.addChild(parent, candidate);
        final IndexBuilder<Graph<?, Node>, Node, Edge, Index<Node, Edge>> indexBuilder = context.getIndexBuilder();
        if ( indexBuilder instanceof IncrementalIndexBuilder ) {
            ((IncrementalIndexBuilder) indexBuilder).addNode(context.getGraphIndex(), candidate);
        }
    }

    @Override
    public Command<RuleManager, RuleViolation> getGraphCommand(final WiresCanvasHandler handler, final GraphCommandFactory factory) {
        return factory.ADD_CHILD_NODE(handler.getDiagram().getGraph(), parent, candidate);
    }

    @Override
    public CommandResult<CanvasCommandViolation> undo(WiresCanvasHandler context) {
        return null;
    }
}
