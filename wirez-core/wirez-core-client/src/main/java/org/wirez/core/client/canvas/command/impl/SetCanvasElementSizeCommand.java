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
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.commands.UpdateElementSizeCommand;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.client.canvas.command.BaseCanvasCommand;
import org.wirez.core.client.canvas.command.CanvasCommand;
import org.wirez.core.client.canvas.impl.BaseCanvas;
import org.wirez.core.client.canvas.impl.BaseCanvasHandler;
import org.wirez.core.client.mutation.HasGraphElementMutation;

/**
 * A Command to update an element's bounds for the given size.
 */
public class SetCanvasElementSizeCommand extends BaseCanvasCommand implements CanvasCommand {

    Element element;
    double w;
    double h;
    
    public SetCanvasElementSizeCommand(final Element element ,
                                       final double w,
                                       final double h) {
        this.element = element;
        this.w = w;
        this.h = h;
    }

    @Override
    protected Command getCommand() {
        return new UpdateElementSizeCommand(element, w, h);
    }

    @Override
    public CanvasCommand apply() {
        final BaseCanvasHandler wirezCanvas = (BaseCanvasHandler) canvasHandler;
        wirezCanvas.updateElementSize(element);
        return this;
    }

    @Override
    public CommandResult execute(final RuleManager ruleManager) {
        return super.execute(ruleManager);
    }
    
    @Override
    public CommandResult undo(RuleManager ruleManager) {
        final CommandResult result = super.execute(ruleManager);
        // TODO
        return result;
    }

    @Override
    public String toString() {
        return "SetCanvasElementSizeCommand [element=" + element.getUUID() + ", w=" + w + ", h=" +  h + "]";
    }
    
    
}
