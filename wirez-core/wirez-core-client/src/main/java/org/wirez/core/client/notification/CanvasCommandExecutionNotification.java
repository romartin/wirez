/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
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

package org.wirez.core.client.notification;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.command.CommandResults;
import org.wirez.core.api.event.NotificationEvent;
import org.wirez.core.api.notification.Notification;
import org.wirez.core.api.util.UUID;

import java.util.Iterator;

public class CanvasCommandExecutionNotification implements Notification<Command> {

    final String notificationUUID;
    final String canvasUUID;
    final Command command;
    final CommandResults results;
    private Type type;

    public CanvasCommandExecutionNotification(String canvasUUID, Command command, CommandResults results) {
        this.notificationUUID = UUID.uuid();
        this.canvasUUID = canvasUUID;
        this.command = command;
        this.results = results;
        this.type = getTypeFromResults();
    }

    @Override
    public String getNotificationUUID() {
        return notificationUUID;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String getSource() {
        return canvasUUID;
    }

    @Override
    public Command getContext() {
        return command;
    }

    private Type getTypeFromResults() {
        boolean hasError = false;
        boolean hasWarn = false;
        final Iterator<CommandResult> iterator = results.results().iterator();
        while (iterator.hasNext()) {
            final CommandResult result = iterator.next();
            if (CommandResult.Type.ERROR.equals(result.getType())) {
                hasError = true;
            } else if (CommandResult.Type.WARNING.equals(result.getType())) {
                hasWarn = true;
            }
        }
        
        if (hasError) {
            return Type.ERROR;
        } else if (hasWarn) {
            return Type.WARNING;
        } else {
            return Type.INFO;
        }
        
    }

    @Override
    public String toString() {
        return "CanvasCommandExecutionNotification [canvasUUID=" + canvasUUID + ", command=" +  command.toString() + "]";
    }

}
