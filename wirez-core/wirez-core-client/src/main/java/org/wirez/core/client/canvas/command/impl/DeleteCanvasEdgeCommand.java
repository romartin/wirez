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
import org.wirez.core.client.canvas.impl.WiresCanvasHandler;
import org.wirez.core.client.factory.ShapeFactory;

public class DeleteCanvasEdgeCommand extends DeleteCanvasElementCommand<Edge> implements HasGraphCommand<WiresCanvasHandler, GraphCommandFactory> {
    
    public DeleteCanvasEdgeCommand(final CanvasCommandFactory canvasCommandFactory, 
                                   final Edge candidate) {
        super(canvasCommandFactory, candidate);
    }

    @Override
    protected Node getParent() {
        return candidate.getSourceNode();
    }

    @Override
    protected void doDeregister(WiresCanvasHandler context) {
        super.doDeregister(context);
        final IndexBuilder<Graph<?, Node>, Node, Edge, Index<Node, Edge>> indexBuilder = context.getIndexBuilder();
        if ( indexBuilder instanceof IncrementalIndexBuilder) {
            ((IncrementalIndexBuilder) indexBuilder).removeEdge(context.getGraphIndex(), candidate);
        }
    }

    @Override
    public Command<RuleManager, RuleViolation> getGraphCommand(WiresCanvasHandler canvasHandler, GraphCommandFactory factory) {
        return factory.DELETE_EDGE(candidate);
    }

    @Override
    public CommandResult<CanvasCommandViolation> undo(WiresCanvasHandler context) {
        return canvasCommandFactory.ADD_EDGE( parent, candidate, factory ).execute( context );
    }
}
