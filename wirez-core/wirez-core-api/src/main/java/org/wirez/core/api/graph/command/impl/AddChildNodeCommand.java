package org.wirez.core.api.graph.command.impl;

import org.uberfire.commons.validation.PortablePreconditions;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;
import org.wirez.core.api.graph.content.ParentChildRelationship;
import org.wirez.core.api.graph.impl.EdgeImpl;
import org.wirez.core.api.util.UUID;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Creates a new node on the target graph and creates/defines a new parent-child connection so new node will be added as a child of 
 * given parent.
 */
public class AddChildNodeCommand extends AbstractGraphCompositeCommand {

    private Graph target;
    private Node parent;
    private Node candidate;
    
    public AddChildNodeCommand(final GraphCommandFactory commandFactory,
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

        // TODO: Create a ParentEdgeFactory iface extending EdgeFactory using as content generics type Relationship
        final String uuid = UUID.uuid();
        final Map<String, Object> properties = new HashMap<>();
        final Set<String> labels = new HashSet<>(1);
        final Edge<ParentChildRelationship, Node> edge = new EdgeImpl<>(uuid, new HashSet<>(), labels, new ParentChildRelationship());
        
        this.addCommand( commandFactory.ADD_NOE(target, candidate) )
            .addCommand( commandFactory.SET_PARENT(parent, candidate, edge) );
    }

    @Override
    public String toString() {
        return "AddChildNodeCommand [parent=" + parent.getUUID() + ", candidate=" + candidate.getUUID() + "]";
    }
    
}
