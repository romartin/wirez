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
package org.wirez.core.api.graph.command;

import org.uberfire.commons.validation.PortablePreconditions;
import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandManager;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.event.NotificationEvent;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.Stack;

/**
 * Default implementation of a CommandManager
 */
@Dependent
public class GraphCommandManager implements CommandManager<RuleManager, RuleViolation> {

    Event<NotificationEvent> notificationEvent;
    
    private final Stack<Stack<Command<RuleManager, RuleViolation>>> commandHistory = new Stack<Stack<Command<RuleManager, RuleViolation>>>();

    @Inject
    public GraphCommandManager(final Event<NotificationEvent> notificationEvent) {
        this.notificationEvent = notificationEvent;
    }

    @SafeVarargs
    @Override
    public final boolean allow(RuleManager ruleManager, Command<RuleManager, RuleViolation>... commands) {
        PortablePreconditions.checkNotNull( "commands", commands );

        for (final Command<RuleManager, RuleViolation> command : commands) {
            final CommandResult<RuleViolation> result = command.allow( ruleManager );
            final GraphCommandResults results = new GraphCommandResults();
            results.getItems().add(result);
            if ( !CommandResult.Type.ERROR.equals(result.getType()) ) {
                return false;
            }
        }
        
        return  true;
    }

    @SafeVarargs
    @Override
    public final GraphCommandResults execute(final RuleManager ruleManager,
                                             final Command<RuleManager, RuleViolation>... commands) {
        PortablePreconditions.checkNotNull( "commands",
                                            commands );

        final GraphCommandResults results = new GraphCommandResults();
        final Stack<Command<RuleManager, RuleViolation>> commandStack = new Stack<>();
        for (final Command<RuleManager, RuleViolation> command : commands) {
            final CommandResult<RuleViolation> result = command.execute( ruleManager );
                commandStack.push(command);
            results.getItems().add(result);
        }
        commandHistory.push(commandStack);
        return results;
    }

    @Override
    public GraphCommandResults undo( final RuleManager ruleManager ) {
        
        Stack<Command<RuleManager, RuleViolation>> commandStack = commandHistory.isEmpty() ? null : commandHistory.pop();
        if ( null != commandStack ) {
            return undo( commandStack, ruleManager );
        }
        
        return new GraphCommandResults();
    }

    private GraphCommandResults undo( final Stack<Command<RuleManager, RuleViolation>> commandStack, final RuleManager ruleManager ) {
        final GraphCommandResults results = new GraphCommandResults();

        if (!commandStack.isEmpty()) {
            
            final int size = commandStack.size();
            for ( int x = 0; x < size; x++) {
                Command<RuleManager, RuleViolation> undoCommand  = commandStack.pop();
                final CommandResult<RuleViolation> undoResult = undoCommand.undo(ruleManager);
                results.results.add(undoResult);
            }
            
        }
        
        return results;
    }

}
