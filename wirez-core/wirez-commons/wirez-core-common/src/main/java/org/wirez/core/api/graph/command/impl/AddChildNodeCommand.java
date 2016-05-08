package org.wirez.core.api.graph.command.impl;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.uberfire.commons.validation.PortablePreconditions;
import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.GraphCommandExecutionContext;
import org.wirez.core.api.graph.command.GraphCommandResultBuilder;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Creates a new node on the target graph and creates/defines a new parent-child connection so new node will be added as a child of 
 * given parent.
 */
@Portable
public final class AddChildNodeCommand extends AbstractGraphCompositeCommand {

    private Graph target;
    private Node parent;
    private Node candidate;
    
    public AddChildNodeCommand(@MapsTo("target") Graph target,
                               @MapsTo("parent") Node parent,
                               @MapsTo("candidate") Node candidate) {
        this.target = PortablePreconditions.checkNotNull( "target",
                target );
        this.parent = PortablePreconditions.checkNotNull( "parent",
                parent );
        this.candidate = PortablePreconditions.checkNotNull( "candidate",
                candidate );
    }
    
    protected void initialize( final GraphCommandExecutionContext context ) {

        this.addCommand( new AddNodeCommand(target, candidate) )
            .addCommand( new AddChildEdgeCommand(parent, candidate) );
    }

    @Override
    protected CommandResult<RuleViolation> doAllow(GraphCommandExecutionContext context, Command<GraphCommandExecutionContext, RuleViolation> command) {
        return check( context );
    }

    @SuppressWarnings("unchecked")
    protected CommandResult<RuleViolation> doCheck(final GraphCommandExecutionContext context) {
        final Collection<RuleViolation> containmentRuleViolations = 
                (Collection<RuleViolation>) context.getRulesManager().containment().evaluate( parent, candidate).violations();
        final Collection<RuleViolation> cardinalityRuleViolations = 
                (Collection<RuleViolation>) context.getRulesManager().cardinality().evaluate( target, candidate, RuleManager.Operation.ADD).violations();
        final Collection<RuleViolation> violations = new LinkedList<RuleViolation>();
        violations.addAll(containmentRuleViolations);
        violations.addAll(cardinalityRuleViolations);
        return new GraphCommandResultBuilder( violations ).build();
    }

    @Override
    public String toString() {
        return "AddChildNodeCommand [parent=" + parent.getUUID() + ", candidate=" + candidate.getUUID() + "]";
    }
    
}
