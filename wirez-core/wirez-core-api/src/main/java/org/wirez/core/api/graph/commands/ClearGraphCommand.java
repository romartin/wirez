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
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.rule.RuleManager;

/**
 * A Command to clear all elements in a graph
 */
public class ClearGraphCommand implements Command {

    private DefaultGraph target;

    public ClearGraphCommand(DefaultGraph target) {
        this.target = PortablePreconditions.checkNotNull( "target",
                target );;
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
            target.clear();
        }
        return results;
    }
    
    private CommandResult check(final RuleManager ruleManager) {
        return new DefaultCommandResult();        
    }

    @Override
    public CommandResult undo(RuleManager ruleManager) {
        // TODO
        return null;
    }

    @Override
    public String toString() {
        return "ClearGraphCommand [target=" + target.getUUID() + "]";
    }
}
