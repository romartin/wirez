package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.graph.processing.index.IncrementalIndexBuilder;
import org.wirez.core.api.graph.processing.index.Index;
import org.wirez.core.api.graph.processing.index.IndexBuilder;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.client.canvas.command.HasGraphCommand;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.command.CanvasCommandViolation;
import org.wirez.core.client.canvas.wires.WiresCanvasHandler;
import org.wirez.core.client.factory.ShapeFactory;
import org.wirez.core.client.impl.BaseConnector;

public class AddCanvasEdgeCommand extends AddCanvasElementCommand<Edge> implements HasGraphCommand<WiresCanvasHandler, GraphCommandFactory>  {
    
    Node parent;
    
    public AddCanvasEdgeCommand(final CanvasCommandFactory canvasCommandFactory, final Node parent, final Edge candidate, final ShapeFactory factory) {
        super(canvasCommandFactory, candidate, factory);
        this.parent = parent;
    }

    @Override
    protected void doRegister(WiresCanvasHandler context) {
        super.doRegister(context);
        final IndexBuilder<Graph<?, Node>, Node, Edge, Index<Node, Edge>> indexBuilder = context.getIndexBuilder();
        if ( indexBuilder instanceof IncrementalIndexBuilder) {
            ((IncrementalIndexBuilder) indexBuilder).addEdge(context.getGraphIndex(), candidate);
        }
    }

    @Override
    public CommandResult<CanvasCommandViolation> execute(final WiresCanvasHandler context) {
        CommandResult<CanvasCommandViolation> result = super.execute(context);
        final String uuid = candidate.getUUID();
        BaseConnector connector = (BaseConnector) context.getCanvas().getShape(uuid);
        connector.applyConnections((Edge<View, Node>) candidate, context);
        return result;
    }

    @Override
    public CommandResult<CanvasCommandViolation> undo(WiresCanvasHandler context) {
        return canvasCommandFactory.DELETE_EDGE( candidate ).execute( context );
    }


    @Override
    public Command<RuleManager, RuleViolation> getGraphCommand(WiresCanvasHandler canvasHandler, GraphCommandFactory factory) {
        return factory.ADD_EDGE(parent, candidate);
    }
    
}
