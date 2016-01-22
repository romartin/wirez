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
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.rule.DefaultRuleManager;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;

import java.util.Collection;
import java.util.List;

/**
 * A Command to delete a DefaultNode from a DefaultGraph
 */
public class DeleteNodeCommand extends AbstractCommand {

    private DefaultGraph target;
    private Node candidate;

    public DeleteNodeCommand(final GraphCommandFactory commandFactory,
                             final DefaultGraph target,
                             final Node candidate ) {
        super(commandFactory);
        this.target = PortablePreconditions.checkNotNull( "target",
                                                          target );
        this.candidate = PortablePreconditions.checkNotNull( "candidate",
                                                             candidate );
    }
    
    @Override
    public CommandResult allow(final RuleManager ruleManager) {
        final CommandResult results = check(ruleManager);
        return results;
    }

    @Override
    public CommandResult execute(final RuleManager ruleManager) {
        final CommandResult results = check(ruleManager);
        if ( !results.getType().equals( CommandResult.Type.ERROR ) ) {
            
            final List<Edge> inEdges = candidate.getInEdges();
            if ( null != inEdges ) {
                for (final Edge inEdge : inEdges) {
                    inEdge.setTargetNode(null);
                }
            }

            final List<Edge> outEdges = candidate.getOutEdges();
            if ( null != outEdges ) {
                for (final Edge outEdge : outEdges) {
                    outEdge.setSourceNode(null);
                }
            }
            
            target.removeNode( candidate.getUUID() );
        }
        return results;
    }
    
    private CommandResult check(final RuleManager ruleManager) {

        final DefaultRuleManager defaultRuleManager = (DefaultRuleManager) ruleManager;
        boolean isNodeInGraph = false;
        for ( Object node : target.nodes() ) {
            if ( node.equals( candidate ) ) {
                isNodeInGraph = true;
                break;
            }
        }

        DefaultCommandResult results;
        if ( isNodeInGraph ) {
            final Collection<RuleViolation> cardinalityRuleViolations = (Collection<RuleViolation>) defaultRuleManager.checkCardinality( target, candidate, RuleManager.Operation.DELETE).violations();
            results = new DefaultCommandResult(cardinalityRuleViolations);
        } else {
            results = new DefaultCommandResult();
            results.setType(CommandResult.Type.ERROR);
            results.setMessage("GraphNode was not present in Graph and hence was not deleted");
        }

        return results;
    }

    @Override
    public CommandResult undo(RuleManager ruleManager) {
        final Command undoCommand = commandFactory.addNodeCommand( target, candidate );
        return undoCommand.execute( ruleManager );
    }

    @Override
    public String toString() {
        return "DeleteNodeCommand [graph=" + target.getUUID() + ", candidate=" + candidate.getUUID() + "]";
    }
    
}
