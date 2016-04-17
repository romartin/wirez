package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.GraphCommandExecutionContext;
import org.wirez.core.api.graph.processing.index.IncrementalIndexBuilder;
import org.wirez.core.api.graph.processing.index.Index;
import org.wirez.core.api.graph.processing.index.IndexBuilder;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.CanvasViolation;
import org.wirez.core.client.shape.factory.ShapeFactory;

public final class AddCanvasNodeCommand extends AddCanvasElementCommand<Node> {

    public AddCanvasNodeCommand(final Node candidate, final ShapeFactory factory) {
        super( candidate, factory );
    }

    @Override
    protected Command<GraphCommandExecutionContext, RuleViolation> buildGraphCommand(final AbstractCanvasHandler context) {
        return context.getGraphCommandFactory().ADD_NODE(context.getDiagram().getGraph(), candidate);
    }

    @Override
    protected void doRegister(AbstractCanvasHandler context) {
        super.doRegister(context);
        final IndexBuilder<Graph<?, Node>, Node, Edge, Index<Node, Edge>> indexBuilder = context.getIndexBuilder();
        if ( indexBuilder instanceof IncrementalIndexBuilder) {
            ((IncrementalIndexBuilder) indexBuilder).addNode(context.getGraphIndex(), candidate);
        }
    }

    @Override
    public CommandResult<CanvasViolation> undo(AbstractCanvasHandler context) {
        return context.getCommandFactory().DELETE_NODE(candidate).execute( context );
    }
}
