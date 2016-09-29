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
package org.wirez.core.graph.command.impl;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.uberfire.commons.validation.PortablePreconditions;
import org.wirez.core.command.CommandResult;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.command.GraphCommandExecutionContext;
import org.wirez.core.graph.command.GraphCommandResultBuilder;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.graph.content.view.ViewConnector;
import org.wirez.core.rule.RuleManager;
import org.wirez.core.rule.RuleViolation;

import java.util.Collection;
import java.util.LinkedList;

/**
 * A Command to set the outgoing connection for an edge.
 * Note that if the connector's source is not set, the <code>sourceNode</code> can be null.
 */
@Portable
public final class SetConnectionSourceNodeCommand extends AbstractGraphCommand {

    private Node<? extends View<?>, Edge> targetNode;
    private Node<? extends View<?>, Edge> lastSourceNode;
    private Node<? extends View<?>, Edge> sourceNode;
    private Edge<? extends View<?>, Node> edge;
    private Integer magnetIndex;
    private Integer lastMagnetIndex;

    @SuppressWarnings("unchecked")
    public SetConnectionSourceNodeCommand(@MapsTo("sourceNode") Node<? extends View<?>, Edge> sourceNode,
                                          @MapsTo("edge") Edge<? extends View<?>, Node> edge,
                                          @MapsTo("magnetIndex") Integer magnetIndex) {
        this.edge = PortablePreconditions.checkNotNull( "edge",
                edge );;
        this.sourceNode = sourceNode;;
        this.magnetIndex = PortablePreconditions.checkNotNull( "magnetIndex",
                magnetIndex);;
        this.lastSourceNode = edge.getSourceNode();
        this.targetNode = edge.getTargetNode();
    }
    
    @Override
    public CommandResult<RuleViolation> allow(final GraphCommandExecutionContext context) {
        return check( context );
    }

    @Override
    public CommandResult<RuleViolation> execute(final GraphCommandExecutionContext context) {
        final CommandResult<RuleViolation> results = check( context );
        if ( !results.getType().equals(CommandResult.Type.ERROR) ) {
            if ( null != lastSourceNode ) {
                lastSourceNode.getOutEdges().remove( edge );
            }
            if ( null != sourceNode ) {
                sourceNode.getOutEdges().add( edge );
            }
            edge.setSourceNode( sourceNode );
            ViewConnector connectionContent = (ViewConnector) edge.getContent();
            lastMagnetIndex = connectionContent.getSourceMagnetIndex();
            connectionContent.setSourceMagnetIndex(magnetIndex);
        }
        return results;
    }

    @SuppressWarnings("unchecked")
    protected CommandResult<RuleViolation> doCheck(final GraphCommandExecutionContext context) {
        final Collection<RuleViolation> connectionRuleViolations = 
                (Collection<RuleViolation>) context.getRulesManager()
                        .connection().evaluate(edge, sourceNode, targetNode).violations();
        final Collection<RuleViolation> cardinalityRuleViolations = 
                (Collection<RuleViolation>) context.getRulesManager()
                        .edgeCardinality()
                        .evaluate(edge, 
                                sourceNode, 
                                targetNode,
                                sourceNode != null ? sourceNode.getOutEdges() : null,
                                targetNode != null ? targetNode.getInEdges() : null, 
                                RuleManager.Operation.ADD)
                        .violations();
        final Collection<RuleViolation> violations = new LinkedList<RuleViolation>();
        violations.addAll(connectionRuleViolations);
        violations.addAll(cardinalityRuleViolations);

        return new GraphCommandResultBuilder( violations ).build();        
    }

    @Override
    public CommandResult<RuleViolation> undo(final GraphCommandExecutionContext context) {
        final SetConnectionTargetNodeCommand undoCommand = new SetConnectionTargetNodeCommand( lastSourceNode, edge, lastMagnetIndex );
        return undoCommand.execute( context );
    }

    @Override
    public String toString() {
        return "SetConnectionSourceNodeCommand [edge=" + edge.getUUID()
                + ", candidate=" + ( null != sourceNode ? sourceNode.getUUID() : "null" )
                + ", magnet=" + magnetIndex + "]";
    }
    
}
