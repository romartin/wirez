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
import org.wirez.core.api.graph.impl.DefaultEdge;
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.rule.RuleManager;

/**
 * A Command to add a DefaultEdge to a Graph
 */
public class AddEdgeCommand implements Command {

    private DefaultGraph target;
    private DefaultEdge edge;

    public AddEdgeCommand(DefaultGraph target, DefaultEdge edge) {
        this.target = PortablePreconditions.checkNotNull( "target",
                target );;
        this.edge = PortablePreconditions.checkNotNull( "edge",
                edge );;
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
            target.addEdge( edge );
        }
        return results;
    }
    
    private CommandResult check(final RuleManager ruleManager) {
        return new DefaultCommandResult();        
    }

    @Override
    public CommandResult undo(RuleManager ruleManager) {
        final Command undoCommand = new DeleteEdgeCommand( target, edge );
        return undoCommand.execute( ruleManager );
    }

    @Override
    public String toString() {
        return "AddEdgeCommand [target=" + target.getUUID() + ", edge=" + edge.getUUID() + "]";
    }
}
