package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.GraphCommandExecutionContext;
import org.wirez.core.api.graph.command.impl.AddChildNodeCommand;
import org.wirez.core.api.graph.processing.index.IncrementalIndexBuilder;
import org.wirez.core.api.graph.processing.index.Index;
import org.wirez.core.api.graph.processing.index.IndexBuilder;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.CanvasViolation;
import org.wirez.core.client.shape.factory.ShapeFactory;

/**
 * TODO: Register the new edge into the canvas hander's index for the graph. 
 */
public final class AddCanvasChildNodeCommand extends AddCanvasElementCommand<Node> {

    protected Node parent;

    public AddCanvasChildNodeCommand(final Node parent, final Node candidate, final ShapeFactory factory) {
        super( candidate, factory );
        this.parent = parent;
    }
    
    @Override
    protected void doRegister(AbstractCanvasHandler context) {
        super.doRegister(context);
        context.addChild(parent, candidate);
        final IndexBuilder<Graph<?, Node>, Node, Edge, Index<Node, Edge>> indexBuilder = context.getIndexBuilder();
        if ( indexBuilder instanceof IncrementalIndexBuilder ) {
            ((IncrementalIndexBuilder) indexBuilder).addNode(context.getGraphIndex(), candidate);
        }
    }

    @Override
    protected Command<GraphCommandExecutionContext, RuleViolation> buildGraphCommand(final AbstractCanvasHandler context) {
        return new AddChildNodeCommand( context.getDiagram().getGraph(), parent, candidate );
    }

    @Override
    public CommandResult<CanvasViolation> undo(final AbstractCanvasHandler context) {
        final DeleteCanvasNodeCommand command = new DeleteCanvasNodeCommand( candidate );
        return command.execute( context );
    }
    
}
