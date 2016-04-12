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
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.GraphCommandExecutionContext;
import org.wirez.core.api.graph.command.GraphCommandResultBuilder;
import org.wirez.core.api.graph.content.relationship.Child;
import org.wirez.core.api.graph.impl.EdgeImpl;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.api.util.UUID;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Creates/defines a new parent-child connection from the given nodes.
 * Both nodes must already be crated and present on the graph storage.
 */
@Portable
public final class AddChildEdgeCommand extends AbstractGraphCommand {

    private Node parent;
    private Node candidate;

    public AddChildEdgeCommand(@MapsTo("parent")  Node parent,
                               @MapsTo("candidate")  Node candidate) {
        this.parent = PortablePreconditions.checkNotNull( "parent",
                parent );
        this.candidate = PortablePreconditions.checkNotNull( "candidate",
                                                             candidate );
    }
    
    @Override
    public CommandResult<RuleViolation> allow( final GraphCommandExecutionContext context ) {
        return check( context );
    }

    @Override
    @SuppressWarnings("unchecked")
    public CommandResult<RuleViolation> execute( final GraphCommandExecutionContext context ) {
        final CommandResult<RuleViolation> results = check( context );

        if ( !results.getType().equals( CommandResult.Type.ERROR ) ) {

            // TODO: Create a ParentEdgeFactory iface extending EdgeFactory using as content generics type Relationship
            final String uuid = UUID.uuid();
            final Set<String> labels = new HashSet<>(1);
            final Edge<Child, Node> edge = new EdgeImpl<>(uuid, labels, new Child());

            edge.setSourceNode(parent);
            edge.setTargetNode(candidate);
            parent.getOutEdges().add( edge );
            candidate.getInEdges().add( edge );
            
        }

        return results;
    }

    @SuppressWarnings("unchecked")
    private CommandResult<RuleViolation> check( final GraphCommandExecutionContext context ) {
        final Collection<RuleViolation> containmentRuleViolations = (Collection<RuleViolation>) context.getRuleManager().checkContainment( parent, candidate).violations();
        final Collection<RuleViolation> violations = new LinkedList<RuleViolation>();
        violations.addAll(containmentRuleViolations);
        return new GraphCommandResultBuilder( violations ).build();
    }

    @Override
    public CommandResult<RuleViolation> undo( final GraphCommandExecutionContext context ) {
        DeleteChildEdgeCommand undoCommand = context.getCommandFactory().DELETE_CHILD_EDGE( parent, candidate );
        return undoCommand.execute( context );
    }

    @Override
    public String toString() {
        return "AddChildEdgeCommand [parent=" + parent.getUUID() + ", candidate=" + candidate.getUUID() + "]";
    }
}
