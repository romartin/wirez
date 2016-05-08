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

public abstract class AbstractCommandManager<C, V> implements CommandManager<C, V> {

    protected Command<C, V> command;
    
    public AbstractCommandManager() {
    }
    
    @Override
    public CommandResult<V>  allow(C context, Command<C, V> command ) {
        PortablePreconditions.checkNotNull( "command", command );
        return doAllow( context, command );
    }

    protected CommandResult<V> doAllow(final C context, final Command<C, V> command) {
        return command.allow( context );
    }

    @Override
    public CommandResult<V> execute(final C context,
                                         final Command<C, V> command) {
        PortablePreconditions.checkNotNull( "command", command );

        CommandResult<V> result = doExecute( context, command );
        
        if ( ! CommandUtils.isError(result) ) {
            this.command = command;
        }
        
        return result;
    }
    
    protected CommandResult<V> doExecute(final C context, final Command<C, V> command) {
        return command.execute( context );
    }

    @Override
    public CommandResult<V> undo(final C context ) {
        
        if ( hasUndoCommand() ) {
            final CommandResult<V> result = doUndo( context, command );
            this.command = null;
            return result;
        }
        
        return null;
    }

    protected CommandResult<V> doUndo( final C context, final Command<C, V> command ) {
        
        if ( null != command ) {
            return command.undo( context );
        }
        
        return null;
    }
    
    protected boolean hasUndoCommand() {
        return null != command;
    }

}
