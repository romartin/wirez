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

import com.ait.lienzo.client.core.shape.Shape;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import org.jboss.errai.bus.client.api.messaging.Message;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.ErrorCallback;
import org.jboss.errai.common.client.api.RemoteCallback;
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
import org.wirez.client.widgets.event.PaletteShapeSelectedEvent;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.graph.Bounds;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.factory.DefaultEdgeFactory;
import org.wirez.core.api.graph.factory.DefaultGraphFactory;
import org.wirez.core.api.graph.factory.DefaultNodeFactory;
import org.wirez.core.api.graph.impl.*;
import org.wirez.core.api.util.Logger;
import org.wirez.core.api.util.UUID;
import org.wirez.core.client.WirezClientManager;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.CanvasSettings;
import org.wirez.core.client.canvas.DefaultCanvasSettingsBuilder;
import org.wirez.core.client.canvas.SelectionManager;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.canvas.command.impl.DefaultCanvasCommands;
import org.wirez.core.client.canvas.impl.DefaultCanvasHandler;
import org.wirez.core.client.factory.ShapeFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.uberfire.commons.validation.PortablePreconditions.checkNotNull;

@Dependent
@WorkbenchScreen(identifier = WirezCanvasScreen.SCREEN_ID )
public class WirezCanvasScreen {

    public static final String SCREEN_ID = "WirezCanvasScreen";

    @Inject
    Canvas canvas;

    @Inject
    CanvasHandler canvasHandler;

    @Inject
    WirezClientManager wirezClientManager;
    
    @Inject
    DefaultCanvasCommands defaultCanvasCommands;

    //@Inject
    //WirezFactoryUtils factoryUtils;
    
    @Inject
    ErrorPopupPresenter errorPopupPresenter;
    
    @Inject
    PlaceManager placeManager;

    @Inject
    Event<ChangeTitleWidgetEvent> changeTitleNotification;
    
    private Menus menu = null;
    private PlaceRequest placeRequest;
    private String uuid;
    private String title = "Wirez Canvas Screen";
    
    @PostConstruct
    public void init() {
        
    }

    @OnStartup
    public void onStartup(final PlaceRequest placeRequest) {

        this.placeRequest = placeRequest;
        String bpmnTestMode = placeRequest.getParameter( "bpmnTestMode", "" );
        if (bpmnTestMode.equals("true")) {
            // showBPMNTestProcess();
        } else {
            String uuid = placeRequest.getParameter( "uuid", "" );
            String wirezSetId = placeRequest.getParameter( "wirezSetId", "" );
            String shapeSetUUID = placeRequest.getParameter( "shapeSetUUID", "" );
            String title = placeRequest.getParameter( "title", "" );

            final DefinitionSet wirezSet = getWirezSet(wirezSetId);
            final DefaultGraphFactory graphFactory = getGraphFactory(wirezSet);

            // For testing...
            final Map<String, Object> properties = new HashMap<String, Object>();
            final Bounds bounds = new DefaultBounds(
                    new DefaultBound(0d,0d),
                    new DefaultBound(0d,0d)
            );

            final DefaultGraph graph = (DefaultGraph) graphFactory.build(uuid, properties, bounds);

            open(uuid, wirezSetId, shapeSetUUID, title, graph);
        }
        
        this.menu = makeMenuBar();
       
    }

    private void open(String uuid, String  definitionSetId, String shapeSetUUID, String title, Graph graph) {

        WirezCanvasScreen.this.title = title;
        changeTitleNotification.fire(new ChangeTitleWidgetEvent(placeRequest, this.title));
        
        CanvasSettings settings = new DefaultCanvasSettingsBuilder()
                .uuid(uuid)
                .definitionSet(definitionSetId)
                .shapeSet(shapeSetUUID)
                .title(title)
                .graph(graph)
                .build();
        
        canvasHandler.initialize(settings, canvas);

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
        
    }

    @OnLostFocus
    public void OnLostFocus() {
        
    }
    
    @WorkbenchMenu
    public Menus getMenu() {
        return menu;
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
                .newTopLevelMenu("Send graph to backend")
                .respondsWith(getSendGraphCommand())
                .endMenu()
                .newTopLevelMenu("Undo")
                .respondsWith(getUndoCommand())
                .endMenu()
                .newTopLevelMenu("Log graph")
                .respondsWith(getLogGraphCommand())
                .endMenu()
                .newTopLevelMenu("Run graph")
                .respondsWith(getRunGraphCommand())
                .endMenu()
                .build();
    }

    private Command getClearGridCommand() {
        return new Command() {
            public void execute() {
                // TODO
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
                // TODO
                /*final Collection<Shape> selectedItems = ((SelectionManager)canvas).getSelectedItems();
                if (selectedItems != null && !selectedItems.isEmpty()) {
                    for (Shape shape : selectedItems) {
                        final Element element = ( (DefaultGraph) canvasHandler.getGraph()).get(shape.getUUID());
                        
                        if (element instanceof Node) {
                            GWT.log("Deleting node with id " + element.getId());
                            wirezCanvas.execute(defaultCommands.DELETE_NODE((DefaultNode) element));
                        } else if (element instanceof Edge) {
                            GWT.log("Deleting edge with id " + element.getId());
                            wirezCanvas.execute(defaultCommands.DELETE_EDGE((DefaultEdge) element));
                        }
                    }
                } else {
                    GWT.log("Cannot delete element, no element selected on canvas.");
                }*/
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

    private Command getSendGraphCommand() {
        return new Command() {
            public void execute() {
                sendGraph();
            }
        };
    }

    private Command getRunGraphCommand() {
        return new Command() {
            public void execute() {
                runGraph();
            }
        };
    }

    private DefinitionSet getWirezSet(final String id) {
        for (final DefinitionSet set : wirezClientManager.getDefinitionSets()) {
            if (set.getId().equals(id)) {
                return set;
            }
        }
        return null;
    }
    
    private final ErrorCallback<Message> errorCallback = new ErrorCallback<Message>() {
        @Override
        public boolean error(final Message message, final Throwable throwable) {
            WirezCanvasScreen.this.showError(throwable);
            return false;
        }
    };
    private void undo() {
        ((CanvasCommandManager)canvasHandler).undo();
    }

    private void logGraph() {
        Logger.log((DefaultGraph) canvasHandler.getGraph());
    }

    private void sendGraph() {
        // TODO
        /*Graph graph = wirezCanvas.getGraph();
        if (graph != null) {
            wirezServices.call(new RemoteCallback<Void>() {
                @Override
                public void callback(final Void aVoid) {
                    Window.alert("Graph send successfully! See server's log output for more details.");
                }
            }, errorCallback).send(graph);
            
        } else {
            Window.alert("Error sending graph - it is NULL");
        }*/
    }
    
    private void runGraph() {
        // TODO: new GraphCanvasSimulator(wirezCanvas).run();
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
    void onPaletteShapeSelected(@Observes PaletteShapeSelectedEvent paletteShapeSelectedEvent) {
            checkNotNull("paletteShapeSelectedEvent", paletteShapeSelectedEvent);
            final ShapeFactory factory = paletteShapeSelectedEvent.getShapeFactory();
            buildShape(factory);
            canvas.draw();
    }
    
    private void buildShape(final ShapeFactory factory) {
        final Wirez w = factoryUtils.getWirez(factory);
        final Element element = buildElement(w);
        if ( w instanceof DefaultNodeFactory) {
            wirezCanvas.execute(defaultCommands.ADD_NODE((DefaultNode) element, factory));
        } else if ( w instanceof DefaultEdgeFactory) {
            wirezCanvas.execute(defaultCommands.ADD_EDGE((DefaultEdge) element, factory));
        }
    }
    
    private Element<? extends Definition> buildElement(final Definition wirez) {
        final Map<String, Object> properties = new HashMap<String, Object>();
        
        final Bounds bounds =
                new DefaultBounds(
                        new DefaultBound(150d, 150d),
                        new DefaultBound(100d, 100d)
                );
        
        return ((GraphElementFactory) wirez).build(UUID.uuid(), properties, bounds);
    }

}
