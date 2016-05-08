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

package org.wirez.core.client.canvas.controls.toolbox;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.jboss.errai.ioc.client.container.SyncBeanDef;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.wirez.core.api.graph.Element;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.controls.AbstractCanvasHandlerRegistrationControl;
import org.wirez.core.client.canvas.controls.toolbox.command.Context;
import org.wirez.core.client.canvas.controls.toolbox.command.ContextImpl;
import org.wirez.core.client.canvas.controls.toolbox.command.ToolboxCommand;
import org.wirez.core.client.components.toolbox.Toolbox;
import org.wirez.core.client.components.toolbox.ToolboxButton;
import org.wirez.core.client.components.toolbox.ToolboxButtonGrid;
import org.wirez.core.client.components.toolbox.ToolboxFactory;
import org.wirez.core.client.components.toolbox.builder.ToolboxBuilder;
import org.wirez.core.client.components.toolbox.builder.ToolboxButtonBuilder;
import org.wirez.core.client.components.toolbox.builder.ToolboxButtonGridBuilder;
import org.wirez.core.client.components.toolbox.event.ToolboxButtonEvent;
import org.wirez.core.client.shape.NodeShape;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.view.ShapeView;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.*;

@Dependent
public class CanvasToolboxControl extends AbstractCanvasHandlerRegistrationControl 
        implements ToolboxControl<AbstractCanvasHandler, Element>, IsWidget {


    private static final int TOOLBOX_PADDING = 2;
    private static final int TOOLBOX_ICON_SIZE = 16;
    
    public interface View extends IsWidget{

        View addWidget(IsWidget widget);

        View clear();

    }

    private final Map<String, Toolbox> toolboxMap = new HashMap<>();
    SyncBeanManager beanManager;
    ToolboxFactory toolboxFactory;
    View view;

    @Inject
    public CanvasToolboxControl(final SyncBeanManager beanManager, 
                                final ToolboxFactory toolboxFactory,
                                final View view) {
        this.beanManager = beanManager;
        this.toolboxFactory = toolboxFactory;
        this.view = view;
    }
    
    @SuppressWarnings("unchecked")
    protected List<ToolboxCommand<?, Object>> getCommands(final Element element ) {

        if ( element.getContent() instanceof  org.wirez.core.api.graph.content.view.View ) {
            final org.wirez.core.api.graph.content.view.View viewContent = 
                    (org.wirez.core.api.graph.content.view.View) element.getContent();

            final List<ToolboxCommand<?, Object>> result = new LinkedList<>();

            // Create a command provider instance for each one available and load the provided commands..
            final Collection<SyncBeanDef<ToolboxControlProvider>> beanDefSets = beanManager.lookupBeans(ToolboxControlProvider.class);
            for (SyncBeanDef<ToolboxControlProvider> providersSet : beanDefSets) {
                final ToolboxControlProvider<Object, Object> commandsProvider  = providersSet.newInstance();
                final Object definition = viewContent.getDefinition();
                
                if ( commandsProvider.supports( definition ) ) {

                    final List<ToolboxCommand<?, Object>> providerCommands = commandsProvider.getCommands( definition );
                    if ( null != providerCommands && !providerCommands.isEmpty() ) {
                        result.addAll( providerCommands );
                    }
                    
                }
            }

            return result;
            
        }
        
        return null;
    }

    @Override
    public void enable(final AbstractCanvasHandler canvasHandler) {
        // Add the control view.
        canvasHandler.getCanvas().addControl( CanvasToolboxControl.this.asWidget() );
        super.enable( canvasHandler );
    }

    @Override
    @SuppressWarnings("unchecked")
    public void register(final Element element) {

        final Shape shape = canvasHandler.getCanvas().getShape( element.getUUID() );

        if ( shape instanceof NodeShape ) {

            final List<ToolboxCommand<?, Object>> commands = getCommands( element );

            if ( null != commands && !commands.isEmpty() ) {

                final ToolboxBuilder<?, ToolboxButtonGrid, ?> toolboxBuilder =
                        (ToolboxBuilder<?, ToolboxButtonGrid, ?>) toolboxFactory.toolboxBuilder();

                final ToolboxButtonGridBuilder buttonGridBuilder = toolboxFactory.toolboxGridBuilder();

                final ToolboxButtonBuilder<Object> buttonBuilder =
                        (ToolboxButtonBuilder<Object>) toolboxFactory.toolboxButtonBuilder();

                final ToolboxButtonGrid grid = buttonGridBuilder
                        .setRows( commands.size() )
                        .setColumns( 1 )
                        .build();

                toolboxBuilder.forView( shape.getShapeView() );
                toolboxBuilder.direction( ToolboxBuilder.Direction.EAST, ToolboxBuilder.Direction.EAST );
                toolboxBuilder.grid( grid );


                for (final ToolboxCommand<?, Object> command : commands) {

                    // TODO: Use command title (tooltip).

                    final ToolboxButton button = buttonBuilder.setIcon( command.getIcon() )
                            .setClickHandler(event -> fireCommandExecution( element, command, event, Context.Event.CLICK) )
                            .setMouseEnterHandler(event -> fireCommandExecution( element, command, event, Context.Event.MOUSE_ENTER) )
                            .setMouseExitHandler(event -> fireCommandExecution( element, command, event, Context.Event.MOUSE_EXIT) )
                            .setDragEndHandler(event -> fireCommandExecution( element, command, event, Context.Event.DRAG) )
                            .build();

                    toolboxBuilder.add( button );

                }

                final Toolbox toolbox = toolboxBuilder.build();

                toolboxMap.put( element.getUUID(), toolbox );

            }
            
        }
            
    }
    
    private void fireCommandExecution(final Element element,
                                      final ToolboxCommand command,
                                      final ToolboxButtonEvent event,
                                      final Context.Event eventType ) {
        
        Context _context = new ContextImpl(canvasHandler,
                eventType,
                event.getX(),
                event.getY());
        
        setCommandView(command).execute(_context, element);
        
    }

    @Override
    public void deregister(final Element element) {

        final Toolbox toolbox = toolboxMap.get( element.getUUID() );
        
        if ( null != toolbox ) {
            
            toolbox.remove();
            
        }
        
    }

    @Override
    public void disable() {
        
        // Delete the control view.
        canvasHandler.getCanvas().deleteControl( CanvasToolboxControl.this.asWidget() );
        
        // De-register all toolbox components.
        for ( final Toolbox toolbox : toolboxMap.values() ) {

            if ( null != toolbox ) {

                toolbox.remove();

            }
            
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
    public Widget asWidget() {
        return view.asWidget();
    }

}
