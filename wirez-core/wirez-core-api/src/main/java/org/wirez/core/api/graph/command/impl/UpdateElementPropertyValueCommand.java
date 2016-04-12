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
import org.wirez.core.api.definition.adapter.PropertyAdapter;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.command.GraphCommandExecutionContext;
import org.wirez.core.api.graph.command.GraphCommandResultBuilder;
import org.wirez.core.api.rule.RuleViolation;

/**
 * A Command to update an element's property.
 */
@Portable
public final class UpdateElementPropertyValueCommand extends AbstractGraphCommand {

    private Element element;
    private String propertyId;
    private Object value;
    private Object oldValue;


    public UpdateElementPropertyValueCommand(@MapsTo("element") Element element,
                                             @MapsTo("propertyId") String propertyId,
                                             @MapsTo("value") Object value) {
        this.element = PortablePreconditions.checkNotNull( "element",
                element );;
        this.propertyId = PortablePreconditions.checkNotNull( "propertyId",
                propertyId );
        this.value = PortablePreconditions.checkNotNull( "value",
                value );
    }
    
    @Override
    public CommandResult<RuleViolation> allow(final GraphCommandExecutionContext context) {
        return GraphCommandResultBuilder.OK_COMMAND;
    }

    @Override
    @SuppressWarnings("unchecked")
    public CommandResult<RuleViolation> execute(final GraphCommandExecutionContext context) {
        final Object p = context.getGraphUtils().getProperty(element, propertyId);
        PropertyAdapter<Object> adapter = context.getDefinitionManager().getPropertyAdapter( p.getClass() ); 
        oldValue = adapter.getValue(p);
        adapter.setValue(p, value);
        return GraphCommandResultBuilder.OK_COMMAND;
    }
    
    @Override
    public CommandResult<RuleViolation> undo(final GraphCommandExecutionContext context) {
        final UpdateElementPropertyValueCommand undoCommand = context.getCommandFactory().UPDATE_PROPERTY_VALUE( element, propertyId, oldValue );
        return undoCommand.execute( context );
    }

    public Object getOldValue() {
        return oldValue;
    }

    @Override
    public String toString() {
        return "UpdateElementPropertyValueCommand [element=" + element.getUUID() + ", property=" + propertyId + ", value=" + value + "]";
    }
    
}
