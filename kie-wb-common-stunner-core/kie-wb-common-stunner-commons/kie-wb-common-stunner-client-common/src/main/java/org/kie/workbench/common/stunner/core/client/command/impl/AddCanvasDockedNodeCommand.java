package org.kie.workbench.common.stunner.core.client.command.impl;

import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.client.command.CanvasViolation;
import org.kie.workbench.common.stunner.core.client.shape.factory.ShapeFactory;
import org.kie.workbench.common.stunner.core.command.Command;
import org.kie.workbench.common.stunner.core.command.CommandResult;
import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Graph;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.command.GraphCommandExecutionContext;
import org.kie.workbench.common.stunner.core.graph.command.impl.AddDockedNodeCommand;
import org.kie.workbench.common.stunner.core.graph.processing.index.IncrementalIndexBuilder;
import org.kie.workbench.common.stunner.core.graph.processing.index.Index;
import org.kie.workbench.common.stunner.core.graph.processing.index.IndexBuilder;
import org.kie.workbench.common.stunner.core.rule.RuleViolation;

/**
 * TODO: Register the new edge into the canvas hander's index for the graph. 
 */
public final class AddCanvasDockedNodeCommand extends AddCanvasElementCommand<Node> {

    protected Node parent;

    public AddCanvasDockedNodeCommand(final Node parent, final Node candidate, final ShapeFactory factory) {
        super( candidate, factory );
        this.parent = parent;
    }
    
    @Override
    @SuppressWarnings("unchecked")
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
        return new AddDockedNodeCommand( context.getDiagram().getGraph(), parent, candidate );
    }

    @Override
    public CommandResult<CanvasViolation> doUndo(final AbstractCanvasHandler context) {
        final DeleteCanvasNodeCommand command = new DeleteCanvasNodeCommand( candidate, parent );
        return command.execute( context );
    }
    
}
