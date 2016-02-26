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

package org.wirez.core.client.factory.control;

import org.wirez.core.client.Shape;
import org.wirez.core.client.control.toolbox.ToolboxControl;
import org.wirez.core.client.control.toolbox.command.MoveDownCommand;
import org.wirez.core.client.control.toolbox.command.MoveUpCommand;
import org.wirez.core.client.control.toolbox.command.NameToolboxCommand;
import org.wirez.core.client.control.toolbox.command.RemoveToolboxCommand;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

@Dependent
@Named("defaultToolboxControlFactory")
public class ToolboxControlFactory implements ShapeControlFactory<Shape, ToolboxControl> {

    ToolboxControl toolboxControl;
    NameToolboxCommand nameToolboxCommand;
    RemoveToolboxCommand removeToolboxCommand;
    MoveUpCommand moveUpCommand;
    MoveDownCommand moveDownCommand;

    @Inject
    public ToolboxControlFactory(final NameToolboxCommand nameToolboxCommand,
                                 final RemoveToolboxCommand removeToolboxCommand,
                                 final ToolboxControl toolboxControl,
                                 final MoveUpCommand moveUpCommand,
                                 final MoveDownCommand moveDownCommand) {
        this.toolboxControl = toolboxControl;
        this.removeToolboxCommand = removeToolboxCommand;
        this.nameToolboxCommand = nameToolboxCommand;
        this.moveUpCommand = moveUpCommand;
        this.moveDownCommand = moveDownCommand;
    }
    
    @PostConstruct
    public void init() {
        defaults();
    }

    @Override
    public ToolboxControl build(final Shape shape) {
        return toolboxControl;
    }
    
    protected void defaults() {
        toolboxControl.clearCommands();
        toolboxControl.addCommand(nameToolboxCommand);
        toolboxControl.addCommand(removeToolboxCommand);
        toolboxControl.addCommand(moveUpCommand);
        toolboxControl.addCommand(moveDownCommand);
    }
    
}
