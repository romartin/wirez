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
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.graph.impl.DefaultEdge;
import org.wirez.core.api.graph.impl.DefaultNode;
import org.wirez.core.api.rule.DefaultRuleManager;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.api.util.Logger;

import java.util.Collection;
import java.util.LinkedList;

/**
 * A Command to set the incoming connection for a DefaultEdge in a Graph.
 */
public class SetConnectionTargetNodeCommand implements Command {

    private DefaultNode<? extends Definition, DefaultEdge> targetNode;
    private DefaultNode<? extends Definition, DefaultEdge> lastTargetNode;
    private DefaultNode<? extends Definition, DefaultEdge> sourceNode;
    private DefaultEdge<? extends Definition, DefaultNode> edge;

    public SetConnectionTargetNodeCommand(final DefaultNode<? extends Definition, DefaultEdge> targetNode,
                                          final DefaultEdge<? extends Definition, DefaultNode> edge) {
        this.edge = PortablePreconditions.checkNotNull( "edge",
                edge );;
        this.targetNode = PortablePreconditions.checkNotNull( "targetNode",
                targetNode);;
        this.lastTargetNode = edge.getTargetNode();
        this.sourceNode = edge.getSourceNode();
        Logger.log("SetConnectionTargetNodeCommand - Edge=" + this.edge.getUUID());
        Logger.log("SetConnectionTargetNodeCommand - Target node=" + ( this.targetNode != null ? this.targetNode.getUUID() : "null") );
        Logger.log("SetConnectionTargetNodeCommand - Last target node=" +  ( this.lastTargetNode != null ? this.lastTargetNode.getUUID() : "null"));
        Logger.log("SetConnectionTargetNodeCommand - Source node=" +  ( this.sourceNode != null ? this.sourceNode.getUUID() : "null"));
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
            if (lastTargetNode != null) {
                lastTargetNode.getInEdges().remove( edge );
            }
            targetNode.getInEdges().add( edge );
            edge.setTargetNode( targetNode );
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
        return "SetConnectionTargetNodeCommand [edge=" + edge.getUUID() + ", candidate=" + targetNode.getUUID() + "]";
    }
    
}
