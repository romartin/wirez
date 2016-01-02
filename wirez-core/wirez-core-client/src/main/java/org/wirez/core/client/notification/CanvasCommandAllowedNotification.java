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

import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.command.CommandResults;
import org.wirez.core.api.notification.Notification;
import org.wirez.core.api.util.UUID;
import org.wirez.core.client.canvas.command.CanvasCommand;

import java.util.Iterator;

public class CanvasCommandAllowedNotification implements Notification<CanvasCommand> {

    final String notificationUUID;
    final String canvasUUID;
    final CanvasCommand command;
    final boolean isAllowed;

    public CanvasCommandAllowedNotification(String canvasUUID, CanvasCommand command, boolean isAllowed) {
        this.notificationUUID = UUID.uuid();
        this.canvasUUID = canvasUUID;
        this.command = command;
        this.isAllowed = isAllowed;
    }

    @Override
    public String getNotificationUUID() {
        return notificationUUID;
    }

    @Override
    public Type getType() {
        return isAllowed ? Type.ERROR : Type.INFO;
    }

    @Override
    public String getSource() {
        return canvasUUID;
    }

    @Override
    public CanvasCommand getContext() {
        return command;
    }

    public boolean isAllowed() {
        return isAllowed;
    }

    @Override
    public String toString() {
        return "CanvasCommandAllowedNotification [canvasUUID=" + canvasUUID + ", command=" +  command.toString() + ", allowed=" + isAllowed + "]";
    }

}
