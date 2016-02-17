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
import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.GraphCommandFactory;
import org.wirez.core.api.graph.command.GraphCommandResult;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;

/**
 * A Command to add a DefaultEdge to a Graph
 */
public class AddEdgeCommand extends AbstractGraphCommand {

    private Node target;
    private Edge edge;

    public AddEdgeCommand(final GraphCommandFactory commandFactory,
                          Node target, Edge edge) {
        super(commandFactory);
        this.target = PortablePreconditions.checkNotNull( "target",
                target );;
        this.edge = PortablePreconditions.checkNotNull( "edge",
                edge );;
    }
    
    @Override
    public CommandResult<RuleViolation> allow(final RuleManager ruleManager) {
        return check(ruleManager);
    }

    @Override
    public CommandResult<RuleViolation> execute(final RuleManager ruleManager) {
        final CommandResult<RuleViolation> results = check(ruleManager);
        if ( !results.getType().equals(CommandResult.Type.ERROR) ) {
            target.getOutEdges().add( edge );
        }
        return results;
    }
    
    private CommandResult<RuleViolation> check(final RuleManager ruleManager) {
        return new GraphCommandResult();        
    }

    @Override
    public CommandResult<RuleViolation> undo(RuleManager ruleManager) {
        final Command<RuleManager, RuleViolation> undoCommand = commandFactory.deleteEdgeCommand( edge );
        return undoCommand.execute( ruleManager );
    }

    @Override
    public String toString() {
        return "AddEdgeCommand [target=" + target.getUUID() + ", edge=" + edge.getUUID() + "]";
    }
}
