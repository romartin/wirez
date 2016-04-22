package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.GraphCommandExecutionContext;
import org.wirez.core.api.graph.command.impl.SafeDeleteNodeCommand;
import org.wirez.core.api.graph.content.relationship.Child;
import org.wirez.core.api.graph.processing.index.IncrementalIndexBuilder;
import org.wirez.core.api.graph.processing.index.Index;
import org.wirez.core.api.graph.processing.index.IndexBuilder;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.CanvasViolation;

import java.util.List;

public final class DeleteCanvasNodeCommand extends DeleteCanvasElementCommand<Node> {

    public DeleteCanvasNodeCommand(final Node candidate) {
        super( candidate );
    }

    @Override
    protected void doDeregister(AbstractCanvasHandler context) {
        if ( null != parent ) {
            context.removeChild( parent, candidate );
        }
        super.doDeregister(context);
        final IndexBuilder<Graph<?, Node>, Node, Edge, Index<Node, Edge>> indexBuilder = context.getIndexBuilder();
        if ( indexBuilder instanceof IncrementalIndexBuilder) {
            ((IncrementalIndexBuilder) indexBuilder).removeNode(context.getGraphIndex(), candidate);
        }
    }

    // TODO: Support for multiple parents.
    @Override
    protected Node getParent() {
        List<Edge> inEdges = candidate.getInEdges();
        if ( null != inEdges && !inEdges.isEmpty() ) {
            for ( final Edge edge : inEdges ) {
                if ( edge.getContent() instanceof Child) {
                    return edge.getSourceNode();
                }
            }
        }
        return null;
    }


    @Override
    protected Command<GraphCommandExecutionContext, RuleViolation> buildGraphCommand(final AbstractCanvasHandler context) {
        return new SafeDeleteNodeCommand(context.getDiagram().getGraph(), candidate);
    }

    @Override
    public CommandResult<CanvasViolation> undo(AbstractCanvasHandler context) {
        final Command<AbstractCanvasHandler, CanvasViolation> command = parent != null ?
                new AddCanvasChildNodeCommand( parent, candidate, factory) :
                new AddCanvasNodeCommand( candidate, factory);
        return command.execute( context );
    }
}
