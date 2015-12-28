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
import org.wirez.core.api.graph.commands.DeleteNodeCommand;
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.graph.impl.DefaultNode;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.client.Shape;
import org.wirez.core.client.canvas.command.BaseCanvasCommand;
import org.wirez.core.client.canvas.command.CanvasCommand;
import org.wirez.core.client.canvas.impl.BaseCanvas;
import org.wirez.core.client.canvas.impl.BaseCanvasHandler;

/**
 * A Command to delete a DefaultNode from a Graph and delete the corresponding canvas shapes.
 */
public class DeleteCanvasNodeCommand extends BaseCanvasCommand {

    DefaultNode candidate;

    public DeleteCanvasNodeCommand(final DefaultNode candidate) {
        this.candidate = candidate;
    }

    @Override
    protected Command getCommand() {
        return new DeleteNodeCommand((DefaultGraph) canvasHandler.getGraph(), candidate);
    }

    @Override
    public CanvasCommand apply() {

        final BaseCanvas baseCanvas = (BaseCanvas) canvasHandler.getCanvas();
        final BaseCanvasHandler wirezCanvas = (BaseCanvasHandler) canvasHandler;
        final Shape shape = baseCanvas.getShape(candidate.getUUID());
        // TODO: Delete connector connections to the node being deleted?
        canvasHandler.getCanvas().deleteShape(shape);
        canvasHandler.getCanvas().draw();
        wirezCanvas.fireElementDeleted(candidate);
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
        return "DeleteCanvasNodeCommand [node=" + candidate.getUUID() + "]";
    }
    

}
