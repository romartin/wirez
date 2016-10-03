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

package org.kie.workbench.common.stunner.core.command.batch;


import org.kie.workbench.common.stunner.core.command.Command;
import org.kie.workbench.common.stunner.core.command.CommandManager;

import java.util.Collection;

/**
 * Manager to handle batched execution of commands.
 */
public interface BatchCommandManager<T, V> extends CommandManager<T, V> {

    /**
     * Add a command to be executed within the current batch.
     */
    BatchCommandManager<T, V> batch( Command<T, V> command );

    /**
     * Execute all commands in batch.
     * If any of the command executions fails, no command must be applied.
     */
    BatchCommandResult<V> executeBatch( T context );

    /**
     * Undo all commands in batch.
     * If any of the command executions fails, no command must be applied.
     */
    BatchCommandResult<V> undoBatch( T context );


    Collection<Command<T, V>> getBatchCommands();

}
