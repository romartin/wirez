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
import org.wirez.core.api.rule.RuleManager;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.Iterator;

/**
 * Default implementation of a CommandManager
 */
@Dependent
public class DefaultCommandManager implements CommandManager<Command> {

    Event<NotificationEvent> notificationEvent;

    @Inject
    public DefaultCommandManager(final Event<NotificationEvent> notificationEvent) {
        this.notificationEvent = notificationEvent;
    }

    @Override
    public boolean allow(RuleManager ruleManager, Command command) {
        PortablePreconditions.checkNotNull( "command",
                command );
        final CommandResult result = command.allow( ruleManager );
        final DefaultCommandResults results = new DefaultCommandResults();
        results.getItems().add(result);
        boolean isAllowed = true;
        if (CommandResult.Type.ERROR.equals(result.getType())) {
            isAllowed = false;
            notifyViolations("CHECKING IF COMMAND IS ALLOWED: " + command.toString(), results);
        }
        
        return isAllowed;
    }

    @Override
    public CommandResults execute(final RuleManager ruleManager,
                                  final Command... commands ) {
        PortablePreconditions.checkNotNull( "commands",
                                            commands );

        final DefaultCommandResults results = new DefaultCommandResults();
        for (final Command command : commands) {
            final CommandResult result = command.execute( ruleManager );
            results.getItems().add(result);
            notifyViolations("EXECUTING COMMAND: " + command.toString(), results);
        }
        return results;
    }
    
    private void notifyViolations(final String uuid, final CommandResults results) {
        if ( null != results ) {
            final Iterable<CommandResult> it = results.results();
            _notifyViolations(uuid, it);
        }
     }
    
    private void _notifyViolations(final String uuid, final Iterable<CommandResult> it) {
        final Iterator<CommandResult> iterator = it.iterator();
        while (iterator.hasNext()) {
            final CommandResult result = iterator.next();
            notificationEvent.fire(new NotificationEvent(getNotificationType(result.getType()), uuid, result.getMessage()));
        }
    }
    
    private NotificationEvent.Type getNotificationType(final CommandResult.Type resultType) {
        if (CommandResult.Type.ERROR.equals(resultType)) {
            return NotificationEvent.Type.ERROR;
        } else if (CommandResult.Type.WARNING.equals(resultType)) {
            return NotificationEvent.Type.WARNING;
        } else {
            return NotificationEvent.Type.INFO;
        }
    }

    @Override
    public CommandResults undo( final RuleManager ruleManager ) {
        // TODO
        return null;
    }

}
