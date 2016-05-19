package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.command.Command;
import org.wirez.core.command.CommandResult;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Graph;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.command.GraphCommandExecutionContext;
import org.wirez.core.graph.command.impl.SafeDeleteNodeCommand;
import org.wirez.core.graph.content.relationship.Child;
import org.wirez.core.graph.processing.index.IncrementalIndexBuilder;
import org.wirez.core.graph.processing.index.Index;
import org.wirez.core.graph.processing.index.IndexBuilder;
import org.wirez.core.rule.RuleViolation;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.CanvasViolation;

import java.util.List;

public final class DeleteCanvasNodeCommand extends DeleteCanvasElementCommand<Node> {

    public DeleteCanvasNodeCommand(final Node candidate) {
        super( candidate );
    }

    public DeleteCanvasNodeCommand(final Node candidate, final Node parent) {
        super( candidate, parent );
    }

    @Override
    protected void doDeregister(AbstractCanvasHandler context) {
        if ( null != parent ) {
            context.removeChild( parent.getUUID(), candidate.getUUID()  );
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
