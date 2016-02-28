package org.wirez.core.api.graph.command.impl;

import org.uberfire.commons.validation.PortablePreconditions;
import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.GraphCommandResult;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;
import org.wirez.core.api.graph.content.Child;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Deletes a node taking into account its ingoing / outgoing edges and safe remove all node's children as well, if any.
 */
public class SafeDeleteNodeCommand extends AbstractGraphCompositeCommand {

    private Graph target;
    private Node candidate;
    
    public SafeDeleteNodeCommand(final GraphCommandFactory commandFactory,
                                 final Graph target,
                                 final Node candidate) {
        super(commandFactory);
        this.target = PortablePreconditions.checkNotNull( "target",
                target );
        this.candidate = PortablePreconditions.checkNotNull( "candidate",
                candidate );
        initCommands();
    }
    
    private void initCommands() {
        
        final List<Command<RuleManager, RuleViolation>> commands = new LinkedList<>();
        
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
        for ( Command<RuleManager, RuleViolation> command : commands ) {
            this.addCommand( command );
        }
        
        // After above commands, just delete the node.
        this.addCommand( commandFactory.DELETE_NODE( target, candidate ) );
        
    }

    @Override
    public CommandResult<RuleViolation> allow(RuleManager context) {
        return check( context );
    }

    private CommandResult<RuleViolation> check(final RuleManager ruleManager) {

        // Check node exist on the store.
        boolean isNodeInGraph = false;
        for ( Object node : target.nodes() ) {
            if ( node.equals( candidate ) ) {
                isNodeInGraph = true;
                break;
            }
        }

        GraphCommandResult results;
        if ( isNodeInGraph ) {
            final Collection<RuleViolation> cardinalityRuleViolations = (Collection<RuleViolation>) ruleManager.checkCardinality( target, candidate, RuleManager.Operation.DELETE).violations();
            results = new GraphCommandResult(cardinalityRuleViolations);
        } else {
            results = new GraphCommandResult();
            results.setType(CommandResult.Type.ERROR);
            results.setMessage("Node was not present in Graph and hence was not deleted");
        }
        
        // Check nodes has no children.
        if ( !results.getType().equals( CommandResult.Type.ERROR ) ) {
            final List<Edge> outEdges = candidate.getOutEdges();
            if ( null != outEdges && !outEdges.isEmpty() ) {
                for (final Edge outEdge : outEdges) {
                    if ( outEdge.getContent() instanceof Child) {
                        results.setType(CommandResult.Type.ERROR);
                        results.setMessage("Node contains children nodes. It cannot be removed using this command [DeleteNodeCommand].");
                        return results;
                    }
                }
            }
        }
        
        return results;
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
