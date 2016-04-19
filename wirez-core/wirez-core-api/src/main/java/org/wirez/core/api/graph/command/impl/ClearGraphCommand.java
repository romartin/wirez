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
import org.wirez.core.api.graph.command.GraphCommandExecutionContext;
import org.wirez.core.api.graph.command.GraphCommandResultBuilder;
import org.wirez.core.api.rule.RuleViolation;

/**
 * A Command to clear all elements in a graph
 * 
 * TODO: Undo.
 */
@Portable
public final class ClearGraphCommand extends AbstractGraphCommand {

    private Graph target;

    public ClearGraphCommand(@MapsTo("target") Graph target) {
        this.target = PortablePreconditions.checkNotNull( "target",
                target );;
    }
    
    @Override
    public CommandResult<RuleViolation> allow(final GraphCommandExecutionContext context) {
        return check( context );
    }

    @Override
    public CommandResult<RuleViolation> execute(final GraphCommandExecutionContext context) {
        final CommandResult<RuleViolation> results = check( context );
        if ( !results.getType().equals(CommandResult.Type.ERROR) ) {
            target.clear();
        }
        return results;
    }
    
    private CommandResult<RuleViolation> check(final GraphCommandExecutionContext context) {
        return GraphCommandResultBuilder.RESULT_OK;        
    }

    @Override
    public CommandResult<RuleViolation> undo(GraphCommandExecutionContext context) {
        // TODO: Return to previous snapshot?
        return null;
    }

    @Override
    public String toString() {
        return "ClearGraphCommand [target=" + target.getUUID() + "]";
    }
}
