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
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.commands.GraphCommandFactory;
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.graph.processing.GraphBoundsIndexer;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.client.canvas.command.BaseCanvasCommand;
import org.wirez.core.client.canvas.command.CanvasCommand;
import org.wirez.core.client.canvas.impl.BaseCanvasHandler;
import org.wirez.core.client.factory.ShapeFactory;

/**
 * A Command to add a DefaultNode to a Graph into a given position, so can it result as a child node, and then add the corresponding canvas shapes for it.
 */
public class AddCanvasNodeAtCommand extends BaseCanvasCommand {

    final Node candidate;
    final double x;
    final double y;
    final ShapeFactory factory;
    
    private Node parent;

    public AddCanvasNodeAtCommand(final GraphCommandFactory commandFactory, 
                                  final Node candidate,
                                  final double x,
                                  final double y,
                                  final ShapeFactory factory ) {
        super(commandFactory);
        this.candidate = candidate;
        this.x = x;
        this.y = y;
        this.factory = factory;
    }

    @Override
    public Command getCommand() {

        final GraphBoundsIndexer boundsIndexer = new GraphBoundsIndexer((DefaultGraph) canvasHandler.getGraphHandler().getGraph());
        this.parent = boundsIndexer.getNodeAt(x , y);
        if ( null == this.parent ) {
            return commandFactory.addNodeCommand((DefaultGraph) canvasHandler.getGraphHandler().getGraph(), candidate);
        } else {
            return commandFactory.addChildNodeCommand((DefaultGraph) canvasHandler.getGraphHandler().getGraph(), this.parent, candidate);
        }
        
    }

    @Override
    public CanvasCommand apply() {
        ( (BaseCanvasHandler) canvasHandler).register(factory, candidate);
        if ( null != this.parent ) {
            ( (BaseCanvasHandler) canvasHandler).addChild(parent, candidate);
        }
        ( (BaseCanvasHandler) canvasHandler).applyElementMutation(candidate);
        return this;
    }

    @Override
    public CommandResult execute(final RuleManager ruleManager) {
        return super.execute(ruleManager);
    }

    @Override
    public CommandResult undo(final RuleManager ruleManager) {
        super.undo(ruleManager);
        final DeleteCanvasNodeCommand undoCommand = new DeleteCanvasNodeCommand( commandFactory, candidate, factory );
        return executeUndoCommand(undoCommand, ruleManager);
    }

    @Override
    public String toString() {
        return "AddCanvasNodeAtCommand [node=" + candidate.getUUID() + ", x=" + x + ", y=" + y + ", factory=" + factory + "]";
    }


}