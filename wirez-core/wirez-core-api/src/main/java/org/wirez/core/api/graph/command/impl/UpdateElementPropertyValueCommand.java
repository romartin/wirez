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
import org.wirez.core.api.adapter.PropertyAdapter;
import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;
import org.wirez.core.api.graph.command.GraphCommandResult;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.api.util.ElementUtils;

import java.util.ArrayList;

/**
 * A Command to update an element's property.
 */
public class UpdateElementPropertyValueCommand extends AbstractGraphCommand {

    private PropertyAdapter adapter;
    private Element element;
    private String propertyId;
    private Object value;
    private Object oldValue;


    public UpdateElementPropertyValueCommand(final GraphCommandFactory commandFactory,
                                             final PropertyAdapter adapter,
                                             final Element element,
                                             final String propertyId,
                                             final Object value) {
        super(commandFactory);
        this.adapter = PortablePreconditions.checkNotNull( "adapter",
                adapter );;
        this.element = PortablePreconditions.checkNotNull( "element",
                element );;
        this.propertyId = PortablePreconditions.checkNotNull( "propertyId",
                propertyId );
        this.value = PortablePreconditions.checkNotNull( "value",
                value );
    }
    
    @Override
    public CommandResult<RuleViolation> allow(final RuleManager ruleManager) {
        // TODO: Check value.
        return new GraphCommandResult();
    }

    @Override
    public CommandResult<RuleViolation> execute(final RuleManager ruleManager) {
        final Property p = ElementUtils.getProperty(element, propertyId);
        oldValue = adapter.getValue(p);
        adapter.setValue(p, value);
        return new GraphCommandResult(new ArrayList<RuleViolation>());
    }
    
    @Override
    public CommandResult<RuleViolation> undo(RuleManager ruleManager) {
        final Command<RuleManager, RuleViolation> undoCommand = commandFactory.updateElementPropertyValueCommand( element, propertyId, oldValue );
        return undoCommand.execute( ruleManager );
    }

    public Object getOldValue() {
        return oldValue;
    }

    @Override
    public String toString() {
        return "UpdateElementPropertyValueCommand [element=" + element.getUUID() + ", property=" + propertyId + ", value=" + value + "]";
    }
    
}
