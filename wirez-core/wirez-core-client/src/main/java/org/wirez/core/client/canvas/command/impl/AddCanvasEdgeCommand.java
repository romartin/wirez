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
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.commands.GraphCommandFactory;
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.client.canvas.command.BaseCanvasCommand;
import org.wirez.core.client.canvas.command.CanvasCommand;
import org.wirez.core.client.canvas.impl.BaseCanvasHandler;
import org.wirez.core.client.factory.ShapeFactory;
import org.wirez.core.client.impl.BaseConnector;

/**
 * A Command to add a DefaultEdge to a Graph and add the corresponding canvas shapes.
 */
public class AddCanvasEdgeCommand extends BaseCanvasCommand {

    Edge candidate;
    ShapeFactory factory;

    public AddCanvasEdgeCommand(final GraphCommandFactory commandFactory, final Edge candidate, final ShapeFactory factory ) {
        super(commandFactory);
        this.candidate = candidate;
        this.factory = factory;
    }

    @Override
    public Command getCommand() {
        return commandFactory.addEdgeCommand((DefaultGraph) canvasHandler.getGraph(), candidate);
    }

    @Override
    public CanvasCommand apply() {
        ( (BaseCanvasHandler) canvasHandler).register(factory, candidate);
        ( (BaseCanvasHandler) canvasHandler).applyElementMutation(candidate);
        final String uuid = candidate.getUUID();
        BaseConnector connector = (BaseConnector) canvasHandler.getCanvas().getShape(uuid);
        connector.applyConnections(candidate, canvasHandler);
        return this;
    }

    @Override
    public CommandResult execute(final RuleManager ruleManager) {
        return super.execute(ruleManager);
    }
    
    @Override
    public CommandResult undo(final RuleManager ruleManager) {
        super.undo(ruleManager);
        final DeleteCanvasEdgeCommand undoCommand = new DeleteCanvasEdgeCommand( commandFactory, candidate, factory );
        return executeUndoCommand(undoCommand, ruleManager);
    }
    
    @Override
    public String toString() {
        return "AddCanvasEdgeCommand [edge=" + candidate.getUUID() + ", factory=" + factory + "]";
    }


}
