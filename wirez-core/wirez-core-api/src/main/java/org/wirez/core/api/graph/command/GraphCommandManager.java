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

import org.wirez.core.api.command.AbstractCommandManager;
import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResults;
import org.wirez.core.api.event.NotificationEvent;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.Stack;

/**
 * Default implementation of a CommandManager for a graph.
 */
@Dependent
public class GraphCommandManager extends AbstractCommandManager<RuleManager, RuleViolation> {

    private final Stack<Stack<Command<RuleManager, RuleViolation>>> commandHistory = new Stack<Stack<Command<RuleManager, RuleViolation>>>();

    @Inject
    public GraphCommandManager(final Event<NotificationEvent> notificationEvent) {
        super(notificationEvent);
    }

    @Override
    protected CommandResults<RuleViolation> buildResults() {
        return new GraphCommandResults();
    }

}
