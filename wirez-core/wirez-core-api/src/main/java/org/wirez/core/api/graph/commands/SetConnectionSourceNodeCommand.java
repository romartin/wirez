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
package org.wirez.core.api.graph.commands;

import org.uberfire.commons.validation.PortablePreconditions;
import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.command.DefaultCommandResult;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.rule.DefaultRuleManager;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;

import java.util.Collection;
import java.util.LinkedList;

/**
 * A Command to set the outgoing connection for a DefaultEdge in a Graph.
 */
public class SetConnectionSourceNodeCommand extends AbstractCommand {

    private Node<? extends ViewContent<?>, Edge> targetNode;
    private Node<? extends ViewContent<?>, Edge> lastSourceNode;
    private Node<? extends ViewContent<?>, Edge> sourceNode;
    private Edge<? extends ViewContent<?>, Node> edge;

    public SetConnectionSourceNodeCommand(final GraphCommandFactory commandFactory,
                                          final Node<? extends ViewContent<?>, Edge> sourceNode,
                                          final Edge<? extends ViewContent<?>, Node> edge) {
        super(commandFactory);
        this.edge = PortablePreconditions.checkNotNull( "edge",
                edge );;
        this.sourceNode = PortablePreconditions.checkNotNull( "sourceNode",
                sourceNode);;
        this.lastSourceNode = edge.getSourceNode();
        this.targetNode = edge.getTargetNode();
    }
    
    @Override
    public CommandResult allow(final RuleManager ruleManager) {
        final CommandResult results = check(ruleManager);
        return results;
    }

    @Override
    public CommandResult execute(final RuleManager ruleManager) {
        final CommandResult results = check(ruleManager);
        if ( !results.getType().equals(CommandResult.Type.ERROR) ) {
            if (lastSourceNode != null) {
                lastSourceNode.getOutEdges().remove( edge );
            }
            sourceNode.getOutEdges().add( edge );
            edge.setSourceNode( sourceNode );
        }
        return results;
    }
    
    private CommandResult check(final RuleManager ruleManager) {
        final DefaultRuleManager defaultRuleManager = (DefaultRuleManager) ruleManager;
        final Collection<RuleViolation> connectionRuleViolations = (Collection<RuleViolation>) defaultRuleManager.checkConnectionRules(sourceNode, targetNode, edge).violations();
        final Collection<RuleViolation> cardinalityRuleViolations = (Collection<RuleViolation>) defaultRuleManager.checkCardinality(sourceNode, targetNode, edge, RuleManager.Operation.ADD).violations();
        final Collection<RuleViolation> violations = new LinkedList<RuleViolation>();
        violations.addAll(connectionRuleViolations);
        violations.addAll(cardinalityRuleViolations);

        final CommandResult results = new DefaultCommandResult(violations);
        return results;        
    }

    @Override
    public CommandResult undo(RuleManager ruleManager) {
        throw new UnsupportedOperationException("Undo for this command not supported yet.");
    }

    @Override
    public String toString() {
        return "SetConnectionSourceNodeCommand [edge=" + edge.getUUID() + ", candidate=" + sourceNode.getUUID() + "]";
    }
    
}
