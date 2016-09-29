package org.kie.workbench.common.stunner.core.graph.command.impl;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.uberfire.commons.validation.PortablePreconditions;
import org.kie.workbench.common.stunner.core.command.Command;
import org.kie.workbench.common.stunner.core.command.CommandResult;
import org.kie.workbench.common.stunner.core.graph.Graph;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.command.GraphCommandExecutionContext;
import org.kie.workbench.common.stunner.core.graph.command.GraphCommandResultBuilder;
import org.kie.workbench.common.stunner.core.rule.RuleViolation;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Creates a new node on the target graph and creates/defines a new dock relationship so new node will be docked into the 
 * given parent.
 */
@Portable
public final class AddDockedNodeCommand extends AbstractGraphCompositeCommand {

    private Graph target;
    private Node parent;
    private Node candidate;
    
    public AddDockedNodeCommand(@MapsTo("target") Graph target,
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
            .addCommand( new AddDockEdgeCommand(parent, candidate) );
    }

    @Override
    protected CommandResult<RuleViolation> doAllow(GraphCommandExecutionContext context, Command<GraphCommandExecutionContext, RuleViolation> command) {
        return check( context );
    }

    @SuppressWarnings("unchecked")
    protected CommandResult<RuleViolation> doCheck(final GraphCommandExecutionContext context) {
        final Collection<RuleViolation> dockingRuleViolations = 
                (Collection<RuleViolation>) context.getRulesManager().docking().evaluate( parent, candidate).violations();
        final Collection<RuleViolation> violations = new LinkedList<RuleViolation>();
        violations.addAll(dockingRuleViolations);
        return new GraphCommandResultBuilder( violations ).build();
    }

    @Override
    public String toString() {
        return "AddDockedNodeCommand [parent=" + parent.getUUID() + ", candidate=" + candidate.getUUID() + "]";
    }
    
}
