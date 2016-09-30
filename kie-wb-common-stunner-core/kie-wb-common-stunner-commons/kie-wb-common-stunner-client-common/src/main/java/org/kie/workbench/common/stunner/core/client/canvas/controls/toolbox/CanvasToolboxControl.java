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

package org.kie.workbench.common.stunner.core.client.canvas.controls.toolbox;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.jboss.errai.ioc.client.container.SyncBeanDef;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.kie.workbench.common.stunner.core.client.canvas.controls.AbstractCanvasHandlerRegistrationControl;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.client.canvas.controls.toolbox.command.Context;
import org.kie.workbench.common.stunner.core.client.canvas.controls.toolbox.command.ContextImpl;
import org.kie.workbench.common.stunner.core.client.canvas.controls.toolbox.command.ToolboxCommand;
import org.kie.workbench.common.stunner.core.client.canvas.event.selection.CanvasClearSelectionEvent;
import org.kie.workbench.common.stunner.core.client.canvas.event.selection.CanvasElementSelectedEvent;
import org.kie.workbench.common.stunner.core.client.components.toolbox.Toolbox;
import org.kie.workbench.common.stunner.core.client.components.toolbox.ToolboxButton;
import org.kie.workbench.common.stunner.core.client.components.toolbox.ToolboxButtonGrid;
import org.kie.workbench.common.stunner.core.client.components.toolbox.ToolboxFactory;
import org.kie.workbench.common.stunner.core.client.components.toolbox.builder.ToolboxBuilder;
import org.kie.workbench.common.stunner.core.client.components.toolbox.builder.ToolboxButtonBuilder;
import org.kie.workbench.common.stunner.core.client.components.toolbox.event.ToolboxButtonEvent;
import org.kie.workbench.common.stunner.core.client.shape.NodeShape;
import org.kie.workbench.common.stunner.core.client.shape.Shape;
import org.kie.workbench.common.stunner.core.graph.Element;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.*;

import static org.uberfire.commons.validation.PortablePreconditions.checkNotNull;

@Dependent
public class CanvasToolboxControl extends AbstractCanvasHandlerRegistrationControl
        implements ToolboxControl<AbstractCanvasHandler, Element>, IsWidget {

    public interface View extends IsWidget {

        View addWidget( IsWidget widget );

        View clear();

    }

    SyncBeanManager beanManager;
    ToolboxFactory toolboxFactory;
    View view;
    private final Map<String, List<Toolbox>> toolboxMap = new HashMap<>();
    private String currentToolboxUUID;

    @Inject
    public CanvasToolboxControl( final SyncBeanManager beanManager,
                                 final ToolboxFactory toolboxFactory,
                                 final View view ) {
        this.beanManager = beanManager;
        this.toolboxFactory = toolboxFactory;
        this.view = view;
        this.currentToolboxUUID = null;
    }

    @SuppressWarnings( "unchecked" )
    protected List<ToolboxControlProvider<AbstractCanvasHandler, Element>> getToolboxProviders( final Element element ) {

        if ( element.getContent() instanceof org.kie.workbench.common.stunner.core.graph.content.view.View ) {

            final org.kie.workbench.common.stunner.core.graph.content.view.View viewContent =
                    ( org.kie.workbench.common.stunner.core.graph.content.view.View ) element.getContent();

            final List<ToolboxControlProvider<AbstractCanvasHandler, Element>> result = new LinkedList<>();

            // Create a command provider instance for each one available and load the provided commands..
            final Collection<SyncBeanDef<ToolboxControlProvider>> beanDefSets =
                    beanManager.lookupBeans( ToolboxControlProvider.class );

            for ( SyncBeanDef<ToolboxControlProvider> providersSet : beanDefSets ) {

                final Object definition = viewContent.getDefinition();

                final ToolboxControlProvider<AbstractCanvasHandler, Element> toolboxProvider = providersSet.newInstance();

                if ( toolboxProvider.supports( definition ) ) {

                    result.add( toolboxProvider );

                }

            }

            return result;

        }

        return null;

    }

    @Override
    public void enable( final AbstractCanvasHandler canvasHandler ) {
        // Add the control view.
        canvasHandler.getCanvas().addControl( CanvasToolboxControl.this.asWidget() );
        super.enable( canvasHandler );
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public void register( final Element element ) {

        final Shape shape = canvasHandler.getCanvas().getShape( element.getUUID() );

        if ( shape instanceof NodeShape ) {

            final List<ToolboxControlProvider<AbstractCanvasHandler, Element>> toolboxControlProviders = getToolboxProviders( element );

            if ( null != toolboxControlProviders && !toolboxControlProviders.isEmpty() ) {

                for ( final ToolboxControlProvider<AbstractCanvasHandler, Element> toolboxControlProvider : toolboxControlProviders ) {

                    final List<ToolboxCommand<?, ?>> commands = toolboxControlProvider.getCommands( canvasHandler, element );

                    if ( null != commands && !commands.isEmpty() ) {

                        final ToolboxBuilder<?, ToolboxButtonGrid, ?> toolboxBuilder =
                                ( ToolboxBuilder<?, ToolboxButtonGrid, ?> ) toolboxFactory.toolboxBuilder();

                        final ToolboxButtonGrid grid = toolboxControlProvider.getGrid( canvasHandler, element );
                        toolboxBuilder.forView( shape.getShapeView() );
                        toolboxBuilder.direction( toolboxControlProvider.getOn(), toolboxControlProvider.getTowards() );
                        toolboxBuilder.grid( grid );

                        final ToolboxButtonBuilder<Object> buttonBuilder =
                                ( ToolboxButtonBuilder<Object> ) toolboxFactory.toolboxButtonBuilder();

                        for ( final ToolboxCommand<?, ?> command : commands ) {

                            // TODO: Use command title (tooltip).

                            final ToolboxButton button = buttonBuilder.setIcon( command.getIcon( grid.getButtonSize(), grid.getButtonSize() ) )
                                    .setClickHandler( event -> fireCommandExecution( element, command, event, Context.Event.CLICK ) )
                                    .setMouseEnterHandler( event -> fireCommandExecution( element, command, event, Context.Event.MOUSE_ENTER ) )
                                    .setMouseExitHandler( event -> fireCommandExecution( element, command, event, Context.Event.MOUSE_EXIT ) )
                                    .setMouseDownHandler( event -> fireCommandExecution( element, command, event, Context.Event.MOUSE_DOWN ) )
                                    .setHoverAnimation( command.getButtonAnimation() )
                                    .build();

                            toolboxBuilder.add( button );

                        }

                        final Toolbox toolbox = toolboxBuilder.build();

                        addToolbox( element.getUUID(), toolbox );

                    }

                }

            }

        }

    }

    private void addToolbox( final String uuid,
                             final Toolbox toolbox ) {

        if ( null != uuid && null != toolbox ) {

            List<Toolbox> toolboxes = toolboxMap.get( uuid );

            if ( null == toolboxes ) {

                toolboxes = new LinkedList<>();

                toolboxMap.put( uuid, toolboxes );

            }

            toolboxes.add( toolbox );
        }

    }

    @SuppressWarnings( "unchecked" )
    private void fireCommandExecution( final Element element,
                                       final ToolboxCommand command,
                                       final ToolboxButtonEvent event,
                                       final Context.Event eventType ) {

        Context _context = new ContextImpl( canvasHandler,
                eventType,
                event.getX(),
                event.getY(),
                event.getClientX(),
                event.getClientY() );

        setCommandView( command ).execute( _context, element );

    }

    @Override
    public void deregister( final Element element ) {

        this.deregister( element.getUUID() );

    }

    private void deregister( final String uuid ) {

        final List<Toolbox> toolboxes = toolboxMap.get( uuid );

        if ( null != toolboxes && !toolboxes.isEmpty() ) {

            for ( final Toolbox toolbox : toolboxes ) {

                toolbox.remove();

            }

            toolboxMap.remove( uuid );

        }

    }

    @Override
    public void deregisterAll() {

        super.deregisterAll();

        final Collection<List<Toolbox>> allToolboxes = toolboxMap.values();

        for( final List<Toolbox> toolboxes : allToolboxes ) {

            for ( final Toolbox toolbox : toolboxes ) {

                toolbox.remove();
            }

        }

        toolboxMap.clear();

    }

    @Override
    protected void doDisable() {

        super.doDisable();

        // Delete the control view.
        canvasHandler.getCanvas().deleteControl( CanvasToolboxControl.this.asWidget() );

        // De-register all toolbox components.
        for ( final List<Toolbox> toolboxes : toolboxMap.values() ) {

            if ( null != toolboxes && !toolboxes.isEmpty() ) {

                for ( final Toolbox toolbox : toolboxes ) {

                    toolbox.remove();

                }

            }

        }

    }

    private ToolboxCommand setCommandView( final ToolboxCommand command ) {

        view.clear();

        if ( command instanceof IsWidget ) {

            view.addWidget( ( ( IsWidget ) command ).asWidget() );
        }

        return command;
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }

    void onCanvasElementSelectedEvent( @Observes CanvasElementSelectedEvent event ) {
        checkNotNull( "event", event );

        if ( checkEventContext(event) ) {

            final String uuid = event.getElementUUID();

            if ( null != uuid ) {

                switchVisibility( uuid );

            }

        }

    }

    void CanvasClearSelectionEvent( @Observes CanvasClearSelectionEvent event ) {
        checkNotNull( "event", event );

        if ( null != this.currentToolboxUUID ) {

            setVisibile( this.currentToolboxUUID, false );

            this.currentToolboxUUID = null;

        }

    }

    private boolean isVisible( final String uuid ) {
        return currentToolboxUUID != null && currentToolboxUUID.equals( uuid );
    }

    private void switchVisibility( final String uuid ) {

        if ( isVisible( uuid ) ) {

            setVisibile( this.currentToolboxUUID, false );

            this.currentToolboxUUID = null;

        } else {

            if ( null != this.currentToolboxUUID ) {

                setVisibile( this.currentToolboxUUID, false );

            }

            setVisibile( uuid, true );

            this.currentToolboxUUID = uuid;

        }

    }

    private void setVisibile( final String uuid, final boolean visible ) {

        final List<Toolbox> toolboxes = toolboxMap.get( uuid );

        if ( null != toolboxes ) {

            for ( final Toolbox toolbox : toolboxes ) {

                if ( visible ) {

                    toolbox.show();

                } else {

                    toolbox.hide();

                }

            }

        }

    }

}
