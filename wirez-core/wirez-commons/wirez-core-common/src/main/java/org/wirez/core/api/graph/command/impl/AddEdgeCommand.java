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
import org.wirez.core.api.rule.RuleViolation;

/**
 * A Command to add an edge into a graph
 */
@Portable
public final class AddEdgeCommand extends AbstractGraphCommand {

    private Node target;
    private Edge edge;

    public AddEdgeCommand(@MapsTo("target") Node target, 
                          @MapsTo("edge") Edge edge) {
        this.target = PortablePreconditions.checkNotNull( "target",
                target );;
        this.edge = PortablePreconditions.checkNotNull( "edge",
                edge );;
    }
    
    @Override
    public CommandResult<RuleViolation> allow(final GraphCommandExecutionContext context) {
        return check( context );
    }

    @Override
    @SuppressWarnings("unchecked")
    public CommandResult<RuleViolation> execute(final GraphCommandExecutionContext context) {
        final CommandResult<RuleViolation> results = check( context );
        if ( !results.getType().equals(CommandResult.Type.ERROR) ) {
            target.getOutEdges().add( edge );
        }
        return results;
    }
    
    protected CommandResult<RuleViolation> doCheck(final GraphCommandExecutionContext context) {
        return GraphCommandResultBuilder.RESULT_OK;        
    }

    @Override
    public CommandResult<RuleViolation> undo(GraphCommandExecutionContext context) {
        final DeleteEdgeCommand undoCommand = new DeleteEdgeCommand( edge );
        return undoCommand.execute( context );
    }

    @Override
    public String toString() {
        return "AddEdgeCommand [target=" + target.getUUID() + ", edge=" + edge.getUUID() + "]";
    }
}
