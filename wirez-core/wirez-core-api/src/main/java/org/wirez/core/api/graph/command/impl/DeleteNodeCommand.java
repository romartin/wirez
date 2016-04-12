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

/**
 * A Command to delete a node from a graph. 
 * Does not take care about node'edges neither children nodes.
 */
@Portable
public final class DeleteNodeCommand extends AbstractGraphCommand {

    private Graph target;
    private Node candidate;

    public DeleteNodeCommand(@MapsTo("target") Graph target,
                             @MapsTo("candidate") Node candidate ) {
        this.target = PortablePreconditions.checkNotNull( "target",
                                                          target );
        this.candidate = PortablePreconditions.checkNotNull( "candidate",
                                                             candidate );
    }
    
    @Override
    public CommandResult<RuleViolation> allow(final GraphCommandExecutionContext context) {
        return checkRules( context );
    }

    @Override
    public CommandResult<RuleViolation> execute(final GraphCommandExecutionContext context) {
        CommandResult<RuleViolation> results = checkRules( context );
        if ( !results.getType().equals( CommandResult.Type.ERROR ) ) {
            target.removeNode( candidate.getUUID() );
        }
        
        return results;
    }
    
    private CommandResult<RuleViolation> checkRules(final GraphCommandExecutionContext context) {

        boolean isNodeInGraph = false;
        for ( Object node : target.nodes() ) {
            if ( node.equals( candidate ) ) {
                isNodeInGraph = true;
                break;
            }
        }

        final GraphCommandResultBuilder builder = new GraphCommandResultBuilder();
        if ( isNodeInGraph ) {
            final Collection<RuleViolation> cardinalityRuleViolations = 
                    (Collection<RuleViolation>) context.getRuleManager().checkCardinality( target, candidate, 
                            RuleManager.Operation.DELETE).violations();
            builder.addViolations( cardinalityRuleViolations );
        } else {
            builder.setType(CommandResult.Type.ERROR);
            builder.setMessage("Node was not present in Graph and hence was not deleted");
        }

        return builder.build();
    }
    
    @Override
    public CommandResult<RuleViolation> undo(GraphCommandExecutionContext context) {
        final AddNodeCommand undoCommand = context.getCommandFactory().ADD_NODE( target, candidate );
        return undoCommand.execute( context );
    }

    @Override
    public String toString() {
        return "DeleteNodeCommand [graph=" + target.getUUID() + ", candidate=" + candidate.getUUID() + "]";
    }
    
}
