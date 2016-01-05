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

package org.wirez.client.workbench.screens;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.IsWidget;
import org.jboss.errai.bus.client.api.messaging.Message;
import org.jboss.errai.common.client.api.ErrorCallback;
import org.uberfire.client.annotations.*;
import org.uberfire.client.mvp.PlaceManager;
import org.uberfire.client.workbench.events.ChangeTitleWidgetEvent;
import org.uberfire.client.workbench.widgets.common.ErrorPopupPresenter;
import org.uberfire.lifecycle.*;
import org.uberfire.mvp.Command;
import org.uberfire.mvp.PlaceRequest;
import org.uberfire.workbench.model.menu.MenuFactory;
import org.uberfire.workbench.model.menu.Menus;
import org.wirez.client.widgets.canvas.Canvas;
import org.wirez.client.widgets.event.AddShapeToCanvasEvent;
import org.wirez.client.workbench.event.CanvasScreenStateChangedEvent;
import org.wirez.client.workbench.util.GraphTests;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.graph.*;
import org.wirez.core.api.graph.factory.DefaultGraphFactory;
import org.wirez.core.api.graph.factory.ElementFactory;
import org.wirez.core.api.graph.impl.*;
import org.wirez.core.api.util.Logger;
import org.wirez.core.api.util.UUID;
import org.wirez.core.client.Shape;
import org.wirez.core.client.ShapeSet;
import org.wirez.core.client.WirezClientManager;
import org.wirez.core.client.canvas.CanvasSettings;
import org.wirez.core.client.canvas.DefaultCanvasSettingsBuilder;
import org.wirez.core.client.canvas.command.impl.MoveCanvasElementCommand;
import org.wirez.core.client.canvas.command.impl.SetCanvasElementSizeCommand;
import org.wirez.core.client.canvas.control.SelectionManager;
import org.wirez.core.client.canvas.command.CanvasCommand;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.canvas.command.impl.DefaultCanvasCommands;
import org.wirez.core.client.canvas.impl.DefaultCanvasHandler;
import org.wirez.core.client.factory.ShapeFactory;
import org.wirez.core.client.util.CanvasHighlightVisitor;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.*;

import static org.uberfire.commons.validation.PortablePreconditions.checkNotNull;

@Dependent
@WorkbenchScreen(identifier = CanvasScreen.SCREEN_ID )
public class CanvasScreen {

    public static final String SCREEN_ID = "CanvasScreen";

    public enum CanvasScreenState {
        ACTIVE, NOT_ACTIVE;
    }

    @Inject
    Canvas canvas;

    @Inject
    DefaultCanvasHandler canvasHandler;

    @Inject
    WirezClientManager wirezClientManager;

    @Inject
    DefaultCanvasCommands defaultCanvasCommands;

    @Inject
    ErrorPopupPresenter errorPopupPresenter;

    @Inject
    PlaceManager placeManager;

    @Inject
    Event<ChangeTitleWidgetEvent> changeTitleNotificationEvent;

    @Inject
    Event<CanvasScreenStateChangedEvent> canvasScreenStateChangedEvent;

    private Menus menu = null;
    private PlaceRequest placeRequest;
    private String uuid;
    private String title = "Canvas Screen";

    @PostConstruct
    public void init() {

    }

    @OnStartup
    public void onStartup(final PlaceRequest placeRequest) {

        this.placeRequest = placeRequest;
        String uuid = placeRequest.getParameter( "uuid", "" );
        String wirezSetId = placeRequest.getParameter( "defSetId", "" );
        String shapeSetUUID = placeRequest.getParameter( "shapeSetId", "" );
        String title = placeRequest.getParameter( "title", "" );
        String graphTestMode = placeRequest.getParameter( "graphTestMode", "false" );
        
        if ("true".equals(graphTestMode)) {

            final DefinitionSet wirezSet = getDefinitionSet("basicSet");
            final ShapeSet shapeSet = getShapeSet("basic");
            final DefaultGraph graph = GraphTests.connectionsTest2();
            open("graphTests", wirezSet, shapeSet, "Graph Tests", graph);
            
        } else {

            final DefinitionSet wirezSet = getDefinitionSet(wirezSetId);
            final ShapeSet shapeSet = getShapeSet(shapeSetUUID);
            final DefaultGraphFactory graphFactory = getGraphFactory(wirezSet);

            // For testing...
            final Map<String, Object> properties = new HashMap<String, Object>();

            final Set<String> labels = new HashSet<>();
            final DefaultGraph graph = (DefaultGraph) graphFactory.build(uuid, labels, properties);

            open(uuid, wirezSet, shapeSet, title, graph);
            
        }
      
        this.menu = makeMenuBar();

    }

    private void open(String uuid, DefinitionSet definitionSet, ShapeSet shapeSet, String title, Graph graph) {

        CanvasScreen.this.title = title;
        changeTitleNotificationEvent.fire(new ChangeTitleWidgetEvent(placeRequest, this.title));

        CanvasSettings settings = new DefaultCanvasSettingsBuilder()
                .uuid(uuid)
                .definitionSet(definitionSet)
                .shapeSet(shapeSet)
                .title(title)
                .graph(graph)
                .canvas(canvas)
                .build();

        canvasHandler.initialize(settings);

        setStateActive();

    }

    private DefaultGraphFactory getGraphFactory(final DefinitionSet definitionSet) {
        Collection<Definition> definitions = definitionSet.getDefinitions();
        for (final Definition definition : definitions) {
            if (definition instanceof DefaultGraphFactory) {
                return (DefaultGraphFactory) definition;
            }
        }
        return null;
    }

    @OnOpen
    public void onOpen() {

    }

    @OnFocus
    public void onFocus() {

    }

    @OnClose
    public void onClose() {
        setStateNotActive();
    }

    @OnLostFocus
    public void OnLostFocus() {
        setStateNotActive();
    }

    @WorkbenchMenu
    public Menus getMenu() {
        return menu;
    }

    private void setStateActive() {
        canvasScreenStateChangedEvent.fire(new CanvasScreenStateChangedEvent(canvasHandler, CanvasScreenState.ACTIVE));
    }

    private void setStateNotActive() {
        canvasScreenStateChangedEvent.fire(new CanvasScreenStateChangedEvent(canvasHandler, CanvasScreenState.NOT_ACTIVE));
    }

    private Menus makeMenuBar() {
        return MenuFactory
                .newTopLevelMenu("Clear grid")
                .respondsWith(getClearGridCommand())
                .endMenu()
                .newTopLevelMenu("Clear selection")
                .respondsWith(getClearSelectionCommand())
                .endMenu()
                .newTopLevelMenu("Delete selected")
                .respondsWith(getDeleteSelectionCommand())
                .endMenu()
                .newTopLevelMenu("Undo")
                .respondsWith(getUndoCommand())
                .endMenu()
                .newTopLevelMenu("Log graph")
                .respondsWith(getLogGraphCommand())
                .endMenu()
                .newTopLevelMenu("Resume graph")
                .respondsWith(getResumeGraphCommand())
                .endMenu()
                .newTopLevelMenu("Visit graph")
                .respondsWith(getVisitGraphCommand())
                .endMenu()
                .build();
    }

    private Command getClearGridCommand() {
        return new Command() {
            public void execute() {
                canvasHandler.execute(defaultCanvasCommands.CLEAR());
            }
        };
    }

    private Command getClearSelectionCommand() {
        return new Command() {
            public void execute() {
                ((SelectionManager)canvas).clearSelection();
            }
        };
    }

    private Command getDeleteSelectionCommand() {
        return new Command() {
            public void execute() {
                final Collection<Shape> selectedItems = ((SelectionManager)canvas).getSelectedItems();
                if (selectedItems != null && !selectedItems.isEmpty()) {
                    for (Shape shape : selectedItems) {
                        Element element = ( (DefaultGraph) canvasHandler.getGraph()).getNode(shape.getId());
                        if (element == null) {
                            element = ( (DefaultGraph) canvasHandler.getGraph()).getEdge(shape.getId());
                            if (element != null) {
                                GWT.log("Deleting edge with id " + element.getUUID());
                                canvasHandler.execute(defaultCanvasCommands.DELETE_EDGE((Edge) element));
                            }
                        } else {
                            GWT.log("Deleting node with id " + element.getUUID());
                            canvasHandler.execute(defaultCanvasCommands.DELETE_NODE((Node) element));

                        }
                    }
                } else {
                    GWT.log("Cannot delete element, no element selected on canvas.");
                }
            }
        };
    }

    private Command getUndoCommand() {
        return new Command() {
            public void execute() {
                undo();
            }
        };
    }

    private Command getLogGraphCommand() {
        return new Command() {
            public void execute() {
                logGraph();
            }
        };
    }

    private Command getResumeGraphCommand() {
        return new Command() {
            public void execute() {
                resumeGraph();
            }
        };
    }
    
    private Command getVisitGraphCommand() {
        return new Command() {
            public void execute() {
                visitGraph();
            }
        };
    }

    private DefinitionSet getDefinitionSet(final String id) {
        for (final DefinitionSet set : wirezClientManager.getDefinitionSets()) {
            if (set.getId().equals(id)) {
                return set;
            }
        }
        return null;
    }

    private ShapeSet getShapeSet(final String id) {
        for (final ShapeSet set : wirezClientManager.getShapeSets()) {
            if (set.getId().equals(id)) {
                return set;
            }
        }
        return null;
    }

    private final ErrorCallback<Message> errorCallback = new ErrorCallback<Message>() {
        @Override
        public boolean error(final Message message, final Throwable throwable) {
            CanvasScreen.this.showError(throwable);
            return false;
        }
    };
    private void undo() {
        ((CanvasCommandManager)canvasHandler).undo();
    }

    private void resumeGraph() {
        Logger.resume((DefaultGraph) canvasHandler.getGraph());
    }

    private void logGraph() {
        Logger.log((DefaultGraph) canvasHandler.getGraph());
    }

    private void visitGraph() {
        new CanvasHighlightVisitor(canvasHandler).run();
    }

    private void showError(final Throwable throwable) {
        errorPopupPresenter.showMessage( throwable != null ? throwable.getMessage() : "Error");
    }

    private void showError(final String message) {
        errorPopupPresenter.showMessage(message);
    }

    @WorkbenchPartTitle
    public String getTitle() {
        return title;
    }

    @WorkbenchPartView
    public IsWidget getWidget() {
        return canvas;
    }

    @WorkbenchContextId
    public String getMyContextRef() {
        return "wirezCanvasScreenContext";
    }

    // TODO: 
    // This event should only have to be processed if the canvas is currently in use, 
    // but it's being processes for all canvas, so any shape added from palette results into all existing canvas screens.
    void AddShapeToCanvasEvent(@Observes AddShapeToCanvasEvent addShapeToCanvasEvent) {
        checkNotNull("addShapeToPaletteEvent", addShapeToCanvasEvent);
        final ShapeFactory factory = addShapeToCanvasEvent.getShapeFactory();
        final Definition definition = addShapeToCanvasEvent.getDefinition();
        final double x = addShapeToCanvasEvent.getX();
        final double y = addShapeToCanvasEvent.getY();
        buildShape(definition, factory, x, y);
        canvas.draw();
    }

    private void buildShape(final Definition definition, final ShapeFactory factory,
                            final double _x, final double _y) {
        final Element<?> element = ((ElementFactory) definition).build(UUID.uuid(), new HashSet<String>(), new HashMap<String, Object>());

        final double x = _x > -1 ? _x : 100d;
        final double y = _y > -1 ? _y : 100d;

        CanvasCommand command = null;
        if ( element instanceof Node) {
            command = defaultCanvasCommands.ADD_NODE((Node) element, factory);
        } else if ( element instanceof Edge) {
            command = defaultCanvasCommands.ADD_EDGE((Edge) element, factory);
        }

        // Add, move and resize the shape.
        if ( null != command ) {
            canvasHandler.execute(command);
            canvasHandler.execute(new MoveCanvasElementCommand(element, x, y));
        }

    }

}
