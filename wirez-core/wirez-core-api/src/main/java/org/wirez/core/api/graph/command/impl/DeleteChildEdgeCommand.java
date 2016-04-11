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
import org.wirez.core.api.graph.content.relationship.Child;
import org.wirez.core.api.graph.content.relationship.Parent;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;

import java.util.List;

/**
 * Deletes a child edge ( from the parent to the child node ).
 */
public class DeleteChildEdgeCommand extends AbstractGraphCommand {

    private Node parent;
    private Node candidate;

    public DeleteChildEdgeCommand(final GraphCommandFactory commandFactory,
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
            final Edge<Parent, Node>  edge = getEdgeForTarget();
            if ( null != edge ) {
                edge.setSourceNode(null);
                edge.setTargetNode(null);
                parent.getOutEdges().remove( edge );
                candidate.getInEdges().remove( edge );
            }
        }
        return results;
    }
    
    private Edge<Parent, Node> getEdgeForTarget() {
        final List<Edge<?, Node>> outEdges = parent.getOutEdges();
        if ( null != outEdges && !outEdges.isEmpty() ) {
            for ( Edge<?, Node> outEdge : outEdges ) {
                if ( outEdge.getContent() instanceof Child) {
                    final Node targetNode = outEdge.getTargetNode();
                    if ( null != targetNode && targetNode.equals( candidate )) {
                        return (Edge<Parent, Node>) outEdge;
                    }
                }
            }
        }
        
        return null;
    }

    private CommandResult<RuleViolation> check(final RuleManager ruleManager) {
        return new GraphCommandResult();
    }

    @Override
    public CommandResult<RuleViolation> undo(RuleManager ruleManager) {
        // TODO
        return null;
    }

    @Override
    public String toString() {
        return "DeleteChildEdgeCommand [parent=" + parent.getUUID() + ", candidate=" + candidate.getUUID() + "]";
    }
}
