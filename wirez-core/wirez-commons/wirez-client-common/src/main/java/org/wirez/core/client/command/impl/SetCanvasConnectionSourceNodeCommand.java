package org.wirez.core.client.command.impl;

import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.command.AbstractCanvasGraphCommand;
import org.wirez.core.client.command.CanvasViolation;
import org.wirez.core.client.shape.MutationContext;
import org.wirez.core.client.util.ShapeUtils;
import org.wirez.core.command.Command;
import org.wirez.core.command.CommandResult;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.command.GraphCommandExecutionContext;
import org.wirez.core.graph.command.impl.SetConnectionSourceNodeCommand;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.rule.RuleViolation;

public final class SetCanvasConnectionSourceNodeCommand extends AbstractCanvasGraphCommand {


    Node<? extends View<?>, Edge> node;
    Edge<? extends View<?>, Node> edge;
    int magnetIndex;
            
    public SetCanvasConnectionSourceNodeCommand(final Node<? extends View<?>, Edge> node,
                                                final Edge<? extends View<?>, Node> edge,
                                                final int magnetIndex) {
        this.node = node;
        this.edge = edge;
        this.magnetIndex = magnetIndex;
    }

    @Override
    protected Command<GraphCommandExecutionContext, RuleViolation> buildGraphCommand(final AbstractCanvasHandler context) {
        return new SetConnectionSourceNodeCommand( node, edge, magnetIndex );
    }

    @Override
    public CommandResult<CanvasViolation> doExecute(final AbstractCanvasHandler context) {
        ShapeUtils.applyConnections( edge, context, MutationContext.STATIC);
        return buildResult();
    }

    @Override
    public CommandResult<CanvasViolation> doUndo(final AbstractCanvasHandler context) {
        return doExecute( context );
    }
}
