package org.wirez.core.client.command.impl;

import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.command.CanvasViolation;
import org.wirez.core.client.shape.factory.ShapeFactory;
import org.wirez.core.command.Command;
import org.wirez.core.command.CommandResult;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Graph;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.command.GraphCommandExecutionContext;
import org.wirez.core.graph.command.impl.AddChildNodeCommand;
import org.wirez.core.graph.processing.index.IncrementalIndexBuilder;
import org.wirez.core.graph.processing.index.Index;
import org.wirez.core.graph.processing.index.IndexBuilder;
import org.wirez.core.rule.RuleViolation;

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
    public CommandResult<CanvasViolation> doUndo(final AbstractCanvasHandler context) {
        final DeleteCanvasNodeCommand command = new DeleteCanvasNodeCommand( candidate, parent );
        return command.execute( context );
    }
    
}
