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

package org.wirez.core.client.canvas.command;

import org.wirez.core.api.command.*;
import org.wirez.core.api.event.NotificationEvent;
import org.wirez.core.client.canvas.impl.WiresCanvasHandler;
import org.wirez.core.client.canvas.impl2.WiresCanvasViewHandler;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Default implementation of a CommandManager for a wires canvas. 
 * Commands apply to the current context, so commands apply to the canvas, graph model is not updated.
 */
@Dependent
@Named("wiresCanvasCommandManager")
public class WiresCanvasCommandManager extends AbstractCommandManager<WiresCanvasViewHandler, CanvasCommandViolation> {

    @Inject
    public WiresCanvasCommandManager(final Event<NotificationEvent> notificationEvent) {
        super(notificationEvent);
    }

    @Override
    protected CommandResult<CanvasCommandViolation> doAllow(WiresCanvasHandler context, Command<WiresCanvasHandler, CanvasCommandViolation> command) {
        // TODO: execute the graph command on a graph command manager.
        return super.doAllow(context, command);
    }

    @Override
    protected CommandResult<CanvasCommandViolation> doExecute(WiresCanvasHandler context, Command<WiresCanvasHandler, CanvasCommandViolation> command) {
        // TODO: execute the graph command on a graph command manager.
        return super.doExecute(context, command);
    }
    
    @Override
    protected CommandResults<CanvasCommandViolation> buildResults() {
        return new CanvasCommandResults();
    }

}
