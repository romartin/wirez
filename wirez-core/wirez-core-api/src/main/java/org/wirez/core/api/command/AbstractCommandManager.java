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
package org.wirez.core.api.command;

import org.uberfire.commons.validation.PortablePreconditions;
import org.wirez.core.api.event.NotificationEvent;

import javax.enterprise.event.Event;
import java.util.Stack;

public abstract class AbstractCommandManager<C, V> implements CommandManager<C, V> {

    Event<NotificationEvent> notificationEvent;
    
    private final Stack<Stack<Command<C, V>>> commandHistory = new Stack<Stack<Command<C, V>>>();

    public AbstractCommandManager(final Event<NotificationEvent> notificationEvent) {
        this.notificationEvent = notificationEvent;
    }
    
    protected abstract CommandResults<V> buildResults();

    @Override
    public boolean allow(C context, Command<C, V>... commands) {
        PortablePreconditions.checkNotNull( "commands", commands );

        for (final Command<C, V> command : commands) {
            final CommandResult<V> result = doAllow( context, command );
            final CommandResults<V> results = buildResults();
            results.add(result);
            if ( CommandResult.Type.ERROR.equals(result.getType()) ) {
                return false;
            }
        }
        
        return  true;
    }

    protected CommandResult<V> doAllow(final C context, final Command<C, V> command) {
        return command.allow( context );
    }

    @Override
    public CommandResults<V> execute(final C context,
                                             final Command<C, V>... commands) {
        PortablePreconditions.checkNotNull( "commands",
                                            commands );

        final CommandResults<V> results = buildResults();
        final Stack<Command<C, V>> commandStack = new Stack<>();
        for (final Command<C, V> command : commands) {
            final CommandResult<V> result = doExecute( context, command );
            commandStack.push(command);
            results.add(result);
        }
        commandHistory.push(commandStack);
        return results;
    }
    
    protected CommandResult<V> doExecute(final C context, final Command<C, V> command) {
        return command.execute( context );
    }

    @Override
    public CommandResults<V> undo( final C context ) {
        
        Stack<Command<C, V>> commandStack = commandHistory.isEmpty() ? null : commandHistory.pop();
        if ( null != commandStack ) {
            return undo( commandStack, context );
        }
        
        return buildResults();
    }

    private CommandResults<V> undo( final Stack<Command<C, V>> commandStack, final C context ) {
        final CommandResults<V> results = buildResults();

        if (!commandStack.isEmpty()) {
            
            final int size = commandStack.size();
            for ( int x = 0; x < size; x++) {
                Command<C, V> undoCommand  = commandStack.pop();
                final CommandResult<V> undoResult = undoCommand.undo(context);
                results.add(undoResult);
            }
            
        }
        
        return results;
    }

}
