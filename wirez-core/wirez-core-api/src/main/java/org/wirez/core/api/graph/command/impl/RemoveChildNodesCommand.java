package org.wirez.core.api.graph.command.impl;

import org.uberfire.commons.validation.PortablePreconditions;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;
import org.wirez.core.api.graph.content.ParentChildRelationship;

import java.util.List;
import java.util.Stack;

public class RemoveChildNodesCommand extends AbstractGraphCompositeCommand {

    private Graph target;
    private Node parent;
    private final Stack<Node> nodesToRemove = new Stack<>();
    
    public RemoveChildNodesCommand(final GraphCommandFactory commandFactory,
                                   final Graph target,
                                   final Node parent) {
        super(commandFactory);
        this.target = PortablePreconditions.checkNotNull( "target",
                target );
        this.parent = PortablePreconditions.checkNotNull( "parent",
                parent );
        initCommands();
    }
    
    private void initCommands() {
        loadChildrenNodes(parent);
        
        for ( final Node nodeToRemove : nodesToRemove ) {
            addDeleteCommands( nodeToRemove );
        }
    }
    
    private void addDeleteCommands(final Node node) {
        this.addCommand( commandFactory.removeParentCommand( parent, node) )
            .addCommand( commandFactory.deleteNodeCommand( target, node ) );
    }

    @Override
    public String toString() {
        return "RemoveChildNodesCommand [parent=" + parent.getUUID() + "]";
    }

    private void loadChildrenNodes(final Node node) {
        final List<Edge<?, Node>> outEdges = node.getOutEdges();
        if ( null != outEdges && !outEdges.isEmpty() ) {
            for ( Edge<?, Node> outEdge : outEdges ) {
                if ( outEdge.getContent() instanceof ParentChildRelationship ) {
                    final Node target = outEdge.getTargetNode();
                    loadChildrenNodes( target );
                    nodesToRemove.add( target );
                }
            }
        }
    }
    
}
