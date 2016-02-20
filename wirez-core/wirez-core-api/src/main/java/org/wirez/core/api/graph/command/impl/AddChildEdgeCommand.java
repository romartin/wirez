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

import org.uberfire.commons.validation.PortablePreconditions;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.GraphCommandResult;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;
import org.wirez.core.api.graph.content.Child;
import org.wirez.core.api.graph.impl.EdgeImpl;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.api.util.UUID;

import java.util.*;

/**
 * Creates/defines a new parent-child connection from the given nodes.
 * Both nodes must already be crated and present on the graph storage.
 */
public class AddChildEdgeCommand extends AbstractGraphCommand {

    private Node parent;
    private Node candidate;

    public AddChildEdgeCommand(final GraphCommandFactory commandFactory,
                               final Node parent,
                               final Node candidate) {
        super(commandFactory);
        this.parent = PortablePreconditions.checkNotNull( "parent",
                parent );
        this.candidate = PortablePreconditions.checkNotNull( "candidate",
                                                             candidate );
    }
    
    @Override
    public CommandResult<RuleViolation> allow(final RuleManager ruleManager) {
        return check(ruleManager);
    }

    @Override
    public CommandResult<RuleViolation> execute(final RuleManager ruleManager) {
        final CommandResult<RuleViolation> results = check(ruleManager);
        if ( !results.getType().equals( CommandResult.Type.ERROR ) ) {

            // TODO: Create a ParentEdgeFactory iface extending EdgeFactory using as content generics type Relationship
            final String uuid = UUID.uuid();
            final Map<String, Object> properties = new HashMap<>();
            final Set<String> labels = new HashSet<>(1);
            final Edge<Child, Node> edge = new EdgeImpl<>(uuid, new HashSet<>(), labels, new Child());

            edge.setSourceNode(parent);
            edge.setTargetNode(candidate);
            parent.getOutEdges().add( edge );
            candidate.getInEdges().add( edge );
            
        }
        return results;
    }
    
    private CommandResult<RuleViolation> check(final RuleManager ruleManager) {
        final Collection<RuleViolation> containmentRuleViolations = (Collection<RuleViolation>) ruleManager.checkContainment( parent, candidate).violations();
        final Collection<RuleViolation> violations = new LinkedList<RuleViolation>();
        violations.addAll(containmentRuleViolations);
        return new GraphCommandResult(violations);
    }

    @Override
    public CommandResult<RuleViolation> undo(RuleManager ruleManager) {
        // TODO
        return null;
    }

    @Override
    public String toString() {
        return "AddChildCommand [parent=" + parent.getUUID() + ", candidate=" + candidate.getUUID() + "]";
    }
}
