package org.wirez.core.api.graph.command.impl;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.uberfire.commons.validation.PortablePreconditions;
import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.GraphCommandExecutionContext;
import org.wirez.core.api.graph.command.GraphCommandResultBuilder;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;
import org.wirez.core.api.graph.content.relationship.Child;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Deletes a node taking into account its ingoing / outgoing edges and safe remove all node's children as well, if any.
 */
@Portable
public final class SafeDeleteNodeCommand extends AbstractGraphCompositeCommand {

    private Graph target;
    private Node candidate;
    
    public SafeDeleteNodeCommand(@MapsTo("target") Graph target,
                                 @MapsTo("candidate") Node candidate) {
        this.target = PortablePreconditions.checkNotNull( "target",
                target );
        this.candidate = PortablePreconditions.checkNotNull( "candidate",
                candidate );
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void initialize(final GraphCommandExecutionContext context) {
        
        final GraphCommandFactory commandFactory = context.getCommandFactory();
        final List<Command<GraphCommandExecutionContext, RuleViolation>> commands = new LinkedList<>();
        
        // Check node's children, if any.
        final List<Node> children = getChildNodes( candidate );
        for ( Node child : children ) {
            commands.add( commandFactory.SAFE_DELETE_NODE( target, child) );
        }
        
        // Check if is a child node, so if exists an ingoing child edge ( from parent node ).
        final List<Edge> inEdges = candidate.getInEdges();
        if ( null != inEdges && !inEdges.isEmpty() ) {
            for (final Edge inEdge : inEdges) {
                if ( inEdge.getContent() instanceof Child) {
                    final Node parent = inEdge.getSourceNode();
                    commands.add( commandFactory.DELETE_CHILD_EDGE( parent, candidate) );
                } else if ( inEdge.getContent() instanceof View ) {
                    commands.add( commandFactory.SET_TARGET_NODE( null, inEdge, 0) );
                }
            }
        }

        final List<Edge> outEdges = candidate.getOutEdges();
        if ( null != outEdges && !outEdges.isEmpty() ) {
            for (final Edge outEdge : outEdges) {
                if ( outEdge.getContent() instanceof View ) {
                    commands.add( commandFactory.SET_SOURCE_NODE( null, outEdge, 0) );
                }
            }
        }
        
        // Add the commands above.
        for ( Command<GraphCommandExecutionContext, RuleViolation> command : commands ) {
            this.addCommand( command );
        }
        
        // After above commands, just delete the node.
        this.addCommand( commandFactory.DELETE_NODE( target, candidate ) );
        
    }

    @Override
    protected CommandResult<RuleViolation> doAllow(GraphCommandExecutionContext context, Command<GraphCommandExecutionContext, RuleViolation> command) {
        return check( context );
    }

    private CommandResult<RuleViolation> check(final GraphCommandExecutionContext context) {

        // Check node exist on the store.
        boolean isNodeInGraph = false;
        for ( Object node : target.nodes() ) {
            if ( node.equals( candidate ) ) {
                isNodeInGraph = true;
                break;
            }
        }

        final GraphCommandResultBuilder builder = new GraphCommandResultBuilder();
        if ( isNodeInGraph ) {
            
            final Collection<RuleViolation> cardinalityRuleViolations = 
                    (Collection<RuleViolation>) context.getRuleManager().checkCardinality( target, candidate, RuleManager.Operation.DELETE).violations();
            builder.addViolations( cardinalityRuleViolations );

            for ( final RuleViolation violation : cardinalityRuleViolations ) {
                if ( builder.isError( violation ) ) {
                    return builder.build();
                }    
            }
            
            
            // Check nodes has no children.
            final List<Edge> outEdges = candidate.getOutEdges();
            if (null != outEdges && !outEdges.isEmpty()) {
                for (final Edge outEdge : outEdges) {
                    if (outEdge.getContent() instanceof Child) {
                        builder.setType(CommandResult.Type.ERROR);
                        builder.setMessage("Node contains children nodes. It cannot be removed using this command [DeleteNodeCommand].");
                    }
                }
            }
            
        } else {
            
            builder.setType(CommandResult.Type.ERROR);
            builder.setMessage("Node was not present in Graph and hence was not deleted");

        }

        return builder.build();
    }

    private List<Node> getChildNodes(final Node node) {
        final List<Node> nodesToRemove = new LinkedList<>();
        final List<Edge<?, Node>> outEdges = node.getOutEdges();
        if ( null != outEdges && !outEdges.isEmpty() ) {
            for ( Edge<?, Node> outEdge : outEdges ) {
                if ( outEdge.getContent() instanceof Child) {
                    final Node target = outEdge.getTargetNode();
                    nodesToRemove.add( target );
                }
            }
        }
        
        return nodesToRemove;
    }

    @Override
    public String toString() {
        return "SafeDeleteNodeCommand [target=" + target.getUUID() + ", candidate=" + candidate.getUUID() + "]";
    }
    
}
