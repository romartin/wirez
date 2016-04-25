/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wirez.core.api.graph.command.impl;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.uberfire.commons.validation.PortablePreconditions;
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
 * A Command to add a node into a graph
 */
@Portable
public final class AddNodeCommand extends AbstractGraphCommand {

    private Graph target;
    private Node candidate;

    public AddNodeCommand(@MapsTo("target") Graph target,
                          @MapsTo("candidate") Node candidate ) {
        this.target = PortablePreconditions.checkNotNull( "target",
                                                          target );
        this.candidate = PortablePreconditions.checkNotNull( "candidate",
                                                             candidate );
    }
    
    @Override
    public CommandResult<RuleViolation> allow(final GraphCommandExecutionContext context) {
        return check( context );
    }

    @Override
    @SuppressWarnings("unchecked")
    public CommandResult<RuleViolation> execute(final GraphCommandExecutionContext context) {
        final CommandResult<RuleViolation> results = check( context );
        if ( !results.getType().equals( CommandResult.Type.ERROR ) ) {
            target.addNode( candidate );
        }
        return results;
    }

    @SuppressWarnings("unchecked")
    private CommandResult<RuleViolation> check(final GraphCommandExecutionContext context) {
        final Collection<RuleViolation> containmentRuleViolations = 
                (Collection<RuleViolation>) context.getRulesManager().containment().evaluate( target, candidate).violations();
        final Collection<RuleViolation> cardinalityRuleViolations = 
                (Collection<RuleViolation>) context.getRulesManager().cardinality().evaluate( target, candidate, RuleManager.Operation.ADD).violations();
        final Collection<RuleViolation> violations = new LinkedList<RuleViolation>();
        violations.addAll(containmentRuleViolations);
        violations.addAll(cardinalityRuleViolations);
        return new GraphCommandResultBuilder( violations ).build();
    }

    @Override
    public CommandResult<RuleViolation> undo(GraphCommandExecutionContext context) {
        final DeleteNodeCommand undoCommand = new DeleteNodeCommand( target, candidate );
        return undoCommand.execute( context );
    }

    @Override
    public String toString() {
        return "AddNodeCommand [target=" + target.getUUID() + ", candidate=" + candidate.getUUID() + "]";
    }
}
