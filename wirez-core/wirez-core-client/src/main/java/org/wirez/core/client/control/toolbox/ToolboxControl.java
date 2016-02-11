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

package org.wirez.core.client.control.toolbox;

import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.shape.SVGPath;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.shared.core.types.Direction;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.wirez.core.api.graph.Element;
import org.wirez.core.client.Shape;
import org.wirez.core.client.canvas.command.impl.DefaultCanvasCommands;
import org.wirez.core.client.control.toolbox.command.Context;
import org.wirez.core.client.control.toolbox.command.ContextImpl;
import org.wirez.core.client.control.toolbox.command.ToolboxCommand;
import org.wirez.core.client.service.ClientDefinitionServices;
import org.wirez.lienzo.toolbox.ButtonsOrRegister;
import org.wirez.lienzo.toolbox.HoverToolbox;
import org.wirez.lienzo.toolbox.HoverToolboxButton;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Dependent
public class ToolboxControl extends BaseToolboxControl<Shape, Element> implements IsWidget {

    public interface View extends IsWidget{
        
        View addWidget(IsWidget widget);
        
        View clear();
        
    }
    
    ClientDefinitionServices clientDefinitionServices;
    HoverToolbox hoverToolbox;
    View view;
    
    private final List<ToolboxCommand> commands = new ArrayList<>();
    
    @Inject
    public ToolboxControl(final DefaultCanvasCommands defaultCanvasCommands,
                          final ClientDefinitionServices clientDefinitionServices,
                          final View view) {
        super(defaultCanvasCommands);
        this.view = view;
        this.clientDefinitionServices = clientDefinitionServices;
    }
    
    public ToolboxControl addCommand(final ToolboxCommand command) {
        commands.add(command);
        return this;
    }

    public ToolboxControl clearCommands() {
        commands.clear();;
        return this;
    }

    @Override
    public void enable(final Shape shape, final Element element) {

        if (shape instanceof WiresShape) {

            WiresShape wiresShape = (WiresShape) shape;
            
            ButtonsOrRegister toolboxBuilder = HoverToolbox.toolboxFor(wiresShape).on(Direction.NORTH_EAST)
                    .towards(Direction.SOUTH);

            for (final ToolboxCommand command : commands) {
                
                toolboxBuilder.add(new HoverToolboxButton(command.getIcon(), new NodeMouseClickHandler() {
                    @Override
                    public void onNodeMouseClick(final NodeMouseClickEvent nodeMouseClickEvent) {
                        Context context = new ContextImpl(canvasHandler, 
                                getCommandManager(), 
                                nodeMouseClickEvent.getX(), 
                                nodeMouseClickEvent.getY());
                        setCommandView(command).execute(context, element);
                    }
                }));
                
            }

            hoverToolbox = toolboxBuilder.register();
            
        }

        
    }
    
    private ToolboxCommand setCommandView(final ToolboxCommand command) {
        view.clear();
        if (command instanceof IsWidget) {
            view.addWidget(((IsWidget) command).asWidget());
        }
        return command;
    }
    
    @Override
    public void disable(final Shape shape) {

        if ( null != hoverToolbox ) {
            hoverToolbox.remove();
        }

    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }
    
    private SVGPath createSVGIcon(final String path) {
        SVGPath icon = new SVGPath( path );
        icon.setFillColor("#000000");
        icon.setStrokeWidth(1);
        return icon;
    }
}
