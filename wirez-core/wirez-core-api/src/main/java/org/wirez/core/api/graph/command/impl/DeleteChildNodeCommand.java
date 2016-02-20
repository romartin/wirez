package org.wirez.core.api.graph.command.impl;

import org.uberfire.commons.validation.PortablePreconditions;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;

public class DeleteChildNodeCommand extends AbstractGraphCompositeCommand {

    private Graph target;
    private Node parent;
    private Node candidate;
    
    public DeleteChildNodeCommand(final GraphCommandFactory commandFactory,
                                  final Graph target,
                                  final Node parent,
                                  final Node candidate) {
        super(commandFactory);
        this.target = PortablePreconditions.checkNotNull( "target",
                target );
        this.parent = PortablePreconditions.checkNotNull( "parent",
                parent );
        this.candidate = PortablePreconditions.checkNotNull( "candidate",
                candidate );
        initCommands();
    }
    
    private void initCommands() {
        this.addCommand( commandFactory.DELETE_PARENT_EDGE( parent, candidate) )
            .addCommand( commandFactory.DELETE_NODE( target, candidate ) );
    }

    @Override
    public String toString() {
        return "DeleteChildNodeCommand [parent=" + parent.getUUID() + ", candidate=" + candidate.getUUID() + "]";
    }
}
