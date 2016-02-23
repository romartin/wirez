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
import org.wirez.core.client.canvas.command.CanvasCommandViolation;

import java.util.Iterator;

public class CanvasCommandExecutionNotification extends AbstractCanvasCommandNotification {

    public CanvasCommandExecutionNotification(final String canvasUUID, 
                                              final CommandResults<CanvasCommandViolation> results,
                                              final boolean isAllowed) {
        super(canvasUUID, results, isAllowed);
    }
    
}
