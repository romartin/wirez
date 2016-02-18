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
import org.wirez.core.api.graph.command.GraphCommandManager;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.client.canvas.impl.WiresCanvasHandler;
import org.wirez.core.client.canvas.impl.WiresCanvasViewHandler;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;

/**
 * Default implementation of a CommandManager for a wires canvas. 
 * Commands apply to the current context, so commands apply to the canvas, graph model is not updated.
 */
@Dependent
@Named("wiresCanvasCommandManager")
public class WiresCanvasCommandManager extends AbstractCommandManager<WiresCanvasHandler, CanvasCommandViolation> {

    CommandManager<RuleManager, RuleViolation> graphCommandManager;
    GraphCommandFactory graphCommandFactory;
    
    @Inject
    public WiresCanvasCommandManager(final Event<NotificationEvent> notificationEvent,
                                     final CommandManager<RuleManager, RuleViolation> graphCommandManager,
                                     final GraphCommandFactory graphCommandFactory) {
        super(notificationEvent);
        this.graphCommandManager = graphCommandManager;
        this.graphCommandFactory = graphCommandFactory;
    }

    @Override
    protected CommandResult<CanvasCommandViolation> doAllow(final WiresCanvasHandler context, 
                                                            final Command<WiresCanvasHandler, CanvasCommandViolation> command) {
    
        if ( command instanceof HasGraphCommand ) {
            final Command<RuleManager, RuleViolation> graphCommand = ((HasGraphCommand) command).getGraphCommand(context, graphCommandFactory);
            final CommandResult<RuleViolation> result = graphCommand.allow( context.getRuleManager() );
            if ( CommandResult.Type.ERROR.equals(result.getType()) ) {
                return buildResult(result);
            }
        }

        return super.doAllow(context, command);

    }

    @Override
    protected CommandResult<CanvasCommandViolation> doExecute(WiresCanvasHandler context, Command<WiresCanvasHandler, CanvasCommandViolation> command) {

        if ( command instanceof HasGraphCommand ) {
            final Command<RuleManager, RuleViolation> graphCommand = ((HasGraphCommand) command).getGraphCommand(context, graphCommandFactory);
            final CommandResult<RuleViolation> result = graphCommand.execute( context.getRuleManager() );
            if ( CommandResult.Type.ERROR.equals(result.getType()) ) {
                return buildResult(result);
            }
        }
        
        return super.doExecute(context, command);
    }

    @Override
    protected CommandResults<CanvasCommandViolation> buildResults() {
        return new CanvasCommandResults();
    }

    protected CommandResult<CanvasCommandViolation> buildResult(final CommandResult<RuleViolation> ruleViolation) {
        final CanvasCommandViolation violation = new CanvasCommandViolationImpl((Collection<RuleViolation>) ruleViolation.getViolations());
        CanvasCommandResult result = new CanvasCommandResult();
        result.setType(ruleViolation.getType());
        result.addViolation(violation);
        return result;
    }
    
}
