package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.command.Command;
import org.wirez.core.command.CommandResult;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Graph;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.command.GraphCommandExecutionContext;
import org.wirez.core.graph.command.impl.DeleteEdgeCommand;
import org.wirez.core.graph.processing.index.IncrementalIndexBuilder;
import org.wirez.core.graph.processing.index.Index;
import org.wirez.core.graph.processing.index.IndexBuilder;
import org.wirez.core.rule.RuleViolation;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.CanvasViolation;

public final class DeleteCanvasEdgeCommand extends DeleteCanvasElementCommand<Edge> {
    
    public DeleteCanvasEdgeCommand(final Edge candidate) {
        super( candidate );
    }

    public DeleteCanvasEdgeCommand(final Edge candidate, final Node sourceNode) {
        super( candidate, sourceNode );
    }

    @Override
    protected Node getParent() {
        return candidate.getSourceNode();
    }

    @Override
    protected void doDeregister(AbstractCanvasHandler context) {
        super.doDeregister(context);
        final IndexBuilder<Graph<?, Node>, Node, Edge, Index<Node, Edge>> indexBuilder = context.getIndexBuilder();
        if ( indexBuilder instanceof IncrementalIndexBuilder) {
            ((IncrementalIndexBuilder) indexBuilder).removeEdge(context.getGraphIndex(), candidate);
        }
    }

    @Override
    protected Command<GraphCommandExecutionContext, RuleViolation> buildGraphCommand(final AbstractCanvasHandler context) {
        return new DeleteEdgeCommand(candidate);
    }

    @Override
    public CommandResult<CanvasViolation> undo(final AbstractCanvasHandler context) {
        return new AddCanvasEdgeCommand( parent, candidate, factory ).execute( context );
    }
}
