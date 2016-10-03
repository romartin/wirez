package org.kie.workbench.common.stunner.core.client.command.impl;

import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.client.command.CanvasViolation;
import org.kie.workbench.common.stunner.core.command.Command;
import org.kie.workbench.common.stunner.core.command.CommandResult;
import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Graph;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.command.GraphCommandExecutionContext;
import org.kie.workbench.common.stunner.core.graph.command.impl.SafeDeleteNodeCommand;
import org.kie.workbench.common.stunner.core.graph.content.relationship.Child;
import org.kie.workbench.common.stunner.core.graph.content.relationship.Dock;
import org.kie.workbench.common.stunner.core.graph.processing.index.IncrementalIndexBuilder;
import org.kie.workbench.common.stunner.core.graph.processing.index.Index;
import org.kie.workbench.common.stunner.core.graph.processing.index.IndexBuilder;
import org.kie.workbench.common.stunner.core.rule.RuleViolation;

import java.util.List;

public final class DeleteCanvasNodeCommand extends DeleteCanvasElementCommand<Node> {

    public DeleteCanvasNodeCommand(final Node candidate) {
        super( candidate );
    }

    public DeleteCanvasNodeCommand(final Node candidate, final Node parent) {
        super( candidate, parent );
    }

    @Override
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
    protected Node getParent() {
        List<Edge> inEdges = candidate.getInEdges();
        if ( null != inEdges && !inEdges.isEmpty() ) {
            for ( final Edge edge : inEdges ) {
                if ( isChildEdge( edge ) || isDockEdge( edge ) ) {
                    return edge.getSourceNode();
                }
                
            }
        }
        
        return null;
    }

    private boolean isChildEdge( final Edge edge ) {
        return edge.getContent() instanceof Child;
    }

    private boolean isDockEdge( final Edge edge ) {
        return edge.getContent() instanceof Dock;
    }

    @Override
    protected Command<GraphCommandExecutionContext, RuleViolation> buildGraphCommand(final AbstractCanvasHandler context) {
        return new SafeDeleteNodeCommand(context.getDiagram().getGraph(), candidate);
    }

    @Override
    public CommandResult<CanvasViolation> doUndo(AbstractCanvasHandler context) {
        final Command<AbstractCanvasHandler, CanvasViolation> command = parent != null ?
                new AddCanvasChildNodeCommand( parent, candidate, factory) :
                new AddCanvasNodeCommand( candidate, factory);
        return command.execute( context );
    }
}
