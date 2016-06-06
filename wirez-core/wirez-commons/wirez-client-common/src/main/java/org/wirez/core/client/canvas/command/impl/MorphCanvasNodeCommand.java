package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.AbstractCanvasGraphCommand;
import org.wirez.core.client.canvas.command.CanvasViolation;
import org.wirez.core.client.shape.MutationContext;
import org.wirez.core.command.Command;
import org.wirez.core.command.CommandResult;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.command.GraphCommandExecutionContext;
import org.wirez.core.graph.command.impl.MorphNodeCommand;
import org.wirez.core.graph.content.definition.Definition;
import org.wirez.core.rule.RuleViolation;

public final class MorphCanvasNodeCommand extends AbstractCanvasGraphCommand {

    private Node<? extends Definition<?>, Edge> candidate;
    private String morphTarget;

    public MorphCanvasNodeCommand( final Node<? extends Definition<?>, Edge> candidate, 
                                   final String morphTarget ) {
        this.candidate = candidate;
        this.morphTarget = morphTarget;
    }

    @Override
    public CommandResult<CanvasViolation> execute(final AbstractCanvasHandler context) {
        context.applyElementMutation( candidate, MutationContext.STATIC );
        return buildResult();
    }

    @Override
    public CommandResult<CanvasViolation> undo(final AbstractCanvasHandler context) {
        return execute( context );
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Command<GraphCommandExecutionContext, RuleViolation> buildGraphCommand(AbstractCanvasHandler context) {
        return new MorphNodeCommand( (Node) candidate, morphTarget );
    }

}
