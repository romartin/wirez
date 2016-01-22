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

import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.commands.GraphCommandFactory;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.client.canvas.CanvasHandler;

public abstract class BaseCanvasCommand implements CanvasCommand {

    protected CanvasHandler canvasHandler;
    protected GraphCommandFactory commandFactory;
    protected Command command;

    public BaseCanvasCommand(GraphCommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    protected abstract Command getCommand();
    
    @Override
    public CanvasCommand setCanvas(final CanvasHandler canvasHandler) {
        this.canvasHandler = canvasHandler;
        this.command = getCommand();
        return this;
    }
    
    @Override
    public CommandResult allow(final RuleManager ruleManager) {
        return command.allow(ruleManager);
    }

    @Override
    public CommandResult execute(final RuleManager ruleManager) {
        return command.execute(ruleManager);
    }

    @Override
    public CommandResult undo(final RuleManager ruleManager) {
        return command.undo(ruleManager);
    }
}
