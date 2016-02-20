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
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;
import org.wirez.core.api.graph.command.GraphCommandResult;
import org.wirez.core.api.graph.content.view.ViewConnector;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;

import java.util.Collection;
import java.util.LinkedList;

/**
 * A Command to set the outgoing connection for a DefaultEdge in a Graph.
 */
public class SetConnectionSourceNodeCommand extends AbstractGraphCommand {

    private Node<? extends View<?>, Edge> targetNode;
    private Node<? extends View<?>, Edge> lastSourceNode;
    private Node<? extends View<?>, Edge> sourceNode;
    private Edge<? extends View<?>, Node> edge;
    private int magnetIndex;

    public SetConnectionSourceNodeCommand(final GraphCommandFactory commandFactory,
                                          final Node<? extends View<?>, Edge> sourceNode,
                                          final Edge<? extends View<?>, Node> edge, 
                                          final int magnetIndex) {
        super(commandFactory);
        this.edge = PortablePreconditions.checkNotNull( "edge",
                edge );;
        this.sourceNode = PortablePreconditions.checkNotNull( "sourceNode",
                sourceNode);;
        this.lastSourceNode = edge.getSourceNode();
        this.targetNode = edge.getTargetNode();
        this.magnetIndex = magnetIndex;
    }
    
    @Override
    public CommandResult<RuleViolation> allow(final RuleManager ruleManager) {
        final CommandResult<RuleViolation> results = check(ruleManager);
        return results;
    }

    @Override
    public CommandResult<RuleViolation> execute(final RuleManager ruleManager) {
        final CommandResult<RuleViolation> results = check(ruleManager);
        if ( !results.getType().equals(CommandResult.Type.ERROR) ) {
            if (lastSourceNode != null) {
                lastSourceNode.getOutEdges().remove( edge );
            }
            sourceNode.getOutEdges().add( edge );
            edge.setSourceNode( sourceNode );
            ViewConnector connectionContent = (ViewConnector) edge.getContent();
            connectionContent.setSourceMagnetIndex(magnetIndex);
        }
        return results;
    }
    
    private CommandResult<RuleViolation> check(final RuleManager ruleManager) {
        final Collection<RuleViolation> connectionRuleViolations = (Collection<RuleViolation>) ruleManager.checkConnectionRules(sourceNode, targetNode, edge).violations();
        final Collection<RuleViolation> cardinalityRuleViolations = (Collection<RuleViolation>) ruleManager.checkCardinality(sourceNode, targetNode, edge, RuleManager.Operation.ADD).violations();
        final Collection<RuleViolation> violations = new LinkedList<RuleViolation>();
        violations.addAll(connectionRuleViolations);
        violations.addAll(cardinalityRuleViolations);

        return new GraphCommandResult(violations);        
    }

    @Override
    public CommandResult<RuleViolation> undo(RuleManager ruleManager) {
        throw new UnsupportedOperationException("Undo for this command not supported yet.");
    }

    @Override
    public String toString() {
        return "SetConnectionSourceNodeCommand [edge=" + edge.getUUID() + ", candidate=" + sourceNode.getUUID() 
                + ", magnet=" + magnetIndex + "]";
    }
    
}
