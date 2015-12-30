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
package org.wirez.core.client.canvas.command.impl;


import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.commands.ClearGraphCommand;
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.client.canvas.command.BaseCanvasCommand;
import org.wirez.core.client.canvas.command.CanvasCommand;
import org.wirez.core.client.canvas.impl.BaseCanvasHandler;

/**
 * A Command to clear a canvas and all the graph's elements..
 */
public class ClearCanvasCommand extends BaseCanvasCommand {

    public ClearCanvasCommand( ) {
    }

    @Override
    protected Command getCommand() {
        return new ClearGraphCommand( (DefaultGraph) canvasHandler.getGraph() );
    }

    @Override
    public CanvasCommand apply() {
        final BaseCanvasHandler baseCanvasHandler = (BaseCanvasHandler) canvasHandler;
        baseCanvasHandler.clear();
        return this;
    }

    @Override
    public CommandResult execute(final RuleManager ruleManager) {
        return super.execute(ruleManager);
    }
    
    @Override
    public CommandResult undo(final RuleManager ruleManager) {
        // TODO
        return super.undo(ruleManager);
    }
    
    @Override
    public String toString() {
        return "ClearCanvasCommand [canvas=" + canvasHandler.getSettings().getUUID() + "]";
    }

}
