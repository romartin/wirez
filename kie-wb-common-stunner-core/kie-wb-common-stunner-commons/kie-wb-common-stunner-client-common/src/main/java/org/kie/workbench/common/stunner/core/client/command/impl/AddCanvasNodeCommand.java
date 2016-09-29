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
import org.kie.workbench.common.stunner.core.graph.command.impl.AddNodeCommand;
import org.kie.workbench.common.stunner.core.graph.processing.index.IncrementalIndexBuilder;
import org.kie.workbench.common.stunner.core.graph.processing.index.Index;
import org.kie.workbench.common.stunner.core.graph.processing.index.IndexBuilder;
import org.kie.workbench.common.stunner.core.rule.RuleViolation;

public final class AddCanvasNodeCommand extends AddCanvasElementCommand<Node> {

    public AddCanvasNodeCommand(final Node candidate, final ShapeFactory factory) {
        super( candidate, factory );
    }

    @Override
    protected Command<GraphCommandExecutionContext, RuleViolation> buildGraphCommand(final AbstractCanvasHandler context) {
        return new AddNodeCommand(context.getDiagram().getGraph(), candidate);
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
    public CommandResult<CanvasViolation> doUndo(AbstractCanvasHandler context) {
        final DeleteCanvasNodeCommand command = new DeleteCanvasNodeCommand( candidate );
        return command.execute( context );
    }
}
