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

import com.google.gwt.logging.client.LogConfiguration;
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
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.processing.GraphBoundsIndexer;
import org.wirez.core.api.rule.EmptyRuleManager;
import org.wirez.core.api.util.ElementUtils;
import org.wirez.core.client.ClientDefinitionManager;
import org.wirez.core.client.Shape;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.canvas.command.CanvasCommandViolation;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.control.SelectionManager;
import org.wirez.core.client.canvas.impl.WiresCanvasHandler;
import org.wirez.core.client.canvas.settings.CanvasSettingsFactory;
import org.wirez.core.client.canvas.settings.WiresCanvasSettings;
import org.wirez.core.client.factory.ShapeFactory;
import org.wirez.core.client.service.ClientDefinitionServices;
import org.wirez.core.client.service.ClientDiagramServices;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.service.ServiceCallback;
import org.wirez.core.client.util.CanvasHighlightVisitor;
import org.wirez.core.client.util.WirezLogger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.uberfire.commons.validation.PortablePreconditions.checkNotNull;

@Dependent
@WorkbenchScreen(identifier = CanvasScreen.SCREEN_ID )
public class CanvasScreen {

    private static Logger LOGGER = Logger.getLogger("org.wirez.client.workbench.screens.CanvasScreen");
    
    public static final String SCREEN_ID = "CanvasScreen";

    public enum CanvasScreenState {
        ACTIVE, NOT_ACTIVE;
    }

    @Inject
    Canvas canvas;

    @Inject
    WiresCanvasHandler canvasHandler;
    
    @Inject
    CanvasSettingsFactory settingsFactory;

    @Inject
    ClientDefinitionManager clientDefinitionManager;
    
    @Inject
    ClientDefinitionServices clientDefinitionServices;
    
    @Inject
    ClientDiagramServices clientDiagramServices;
    
    @Inject
    ShapeManager shapeManager;

    @Inject
    CanvasCommandFactory canvasCommandFactory;

    @Inject
    ErrorPopupPresenter errorPopupPresenter;

    @Inject
    PlaceManager placeManager;
    
    @Inject
    EmptyRuleManager emptyRuleManager;

    @Inject
    Event<ChangeTitleWidgetEvent> changeTitleNotificationEvent;

    @Inject
    Event<CanvasScreenStateChangedEvent> canvasScreenStateChangedEvent;

    private Menus menu = null;
    private PlaceRequest placeRequest;
    private String title = "Canvas Screen";

    @PostConstruct
    public void init() {

    }

    /**
     * Expected settings:
     * - existing diagram
     *      - path
     * - new diagram
     *      - defSetId
     *      - shapeSetId
     *      - title
     */
    @OnStartup
    public void onStartup(final PlaceRequest placeRequest) {

        this.placeRequest = placeRequest;
        final String path = placeRequest.getParameter( "path", "" );

        CanvasScreen.this.menu = makeMenuBar();
        
        
        
        final boolean isCreate = path == null || path.trim().length() == 0;
        
        if (isCreate) {

            final String defSetId = placeRequest.getParameter( "defSetId", "" );
            final String shapeSetUUID = placeRequest.getParameter( "shapeSetId", "" );
            final String title = placeRequest.getParameter( "title", "" );

            clientDiagramServices.create(defSetId, shapeSetUUID, title, new ServiceCallback<Diagram>() {
                @Override
                public void onSuccess(final Diagram diagram) {
                    open(diagram);
                }

                @Override
                public void onError(final ClientRuntimeError error) {
                    showError(error);
                }
            });
            
        } else {
         
            clientDiagramServices.load(path, new ServiceCallback<Diagram>() {
                @Override
                public void onSuccess(final Diagram diagram) {
                    open(diagram);
                }

                @Override
                public void onError(final ClientRuntimeError error) {
                    showError(error);
                }
            });
            
        }
        
    }

    private void open(final Diagram diagram) {

        final String title = diagram.getSettings().getTitle();
        final Graph graph = diagram.getGraph();
        
        final ViewContent viewContent = (ViewContent) graph.getContent();
        final Double[] graphSize = ElementUtils.getSize(viewContent);
        
        
        // Show the graph a canvas instance.
        WiresCanvasSettings settings = settingsFactory.getDefaultSettings();

        canvas.show( graphSize[0].intValue() , graphSize[1].intValue() );
        
        canvasHandler.initialize(canvas, diagram, settings);

        // Change screen title.
        CanvasScreen.this.title = title;
        changeTitleNotificationEvent.fire(new ChangeTitleWidgetEvent(placeRequest, this.title));

        // Active the screen.
        setStateActive();

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
                .newTopLevelMenu("Switch log level")
                .respondsWith(getSwitchLogLevelCommand())
                .endMenu()
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

    private Command getSwitchLogLevelCommand() {
        return new Command() {
            public void execute() {
                final Level level = Logger.getLogger("").getLevel();
                final Level newLevel = Level.SEVERE.equals(level) ? Level.FINE : Level.SEVERE;
                LOGGER.log(Level.SEVERE, "Switching to log level [" + newLevel.getName() + "]");
                Logger.getLogger("").setLevel(newLevel);
            }
        };
    }

    private Command getClearGridCommand() {
        return new Command() {
            public void execute() {
                CanvasScreen.this.execute( canvasCommandFactory.CLEAR_CANVAS() );
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
                        Element element = canvasHandler.getGraphHandler().getNode(shape.getId());
                        if (element == null) {
                            element = canvasHandler.getGraphHandler().getEdge(shape.getId());
                            if (element != null) {
                                log(Level.FINE, "Deleting edge with id " + element.getUUID());
                                CanvasScreen.this.execute( canvasCommandFactory.DELETE_EDGE( (Edge) element ));
                            }
                        } else {
                            log(Level.FINE, "Deleting node with id " + element.getUUID());
                            CanvasScreen.this.execute( canvasCommandFactory.DELETE_NODE( (Node) element ));

                        }
                    }
                } else {
                    log(Level.FINE, "Cannot delete element, no element selected on canvas.");
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

    private interface DefinitionSetRequestCallback {
        void onSuccess ( DefinitionSet definitionSet );
    }
    
    

    private final ErrorCallback<Message> errorCallback = new ErrorCallback<Message>() {
        @Override
        public boolean error(final Message message, final Throwable throwable) {
            CanvasScreen.this.showError(throwable);
            return false;
        }
    };
    
    private void execute(final org.wirez.core.api.command.Command<WiresCanvasHandler, CanvasCommandViolation> command) {
        canvasHandler.execute( command );
    }
    
    private void undo() {
        canvasHandler.undo();
    }

    private void resumeGraph() {
        WirezLogger.resume( canvasHandler.getDiagram().getGraph());
    }

    private void logGraph() {
        WirezLogger.log( canvasHandler.getDiagram().getGraph());
    }

    private void visitGraph() {
        new CanvasHighlightVisitor(canvasHandler).run();
    }

    private void showError(final ClientRuntimeError error) {
        final String message = error.getCause() != null ? error.getCause() : error.getMessage();
        showError(message);
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
        
        
        clientDefinitionServices.buildGraphElement(org.wirez.core.api.util.UUID.uuid(), definition, new ServiceCallback<Element>() {
            @Override
            public void onSuccess(final Element item) {
                final Element<?> element = (Element<?>) item;

                double x = _x > -1 ? _x : 100d;
                double y = _y > -1 ? _y : 100d;

                Node<ViewContent<?>, Edge> parent = null;
                if ( _x > -1 && _y > -1) {
                    final GraphBoundsIndexer boundsIndexer = new GraphBoundsIndexer(canvasHandler.getDiagram().getGraph());
                    parent = boundsIndexer.getNodeAt(_x, _y);
                    final Double[] parentCoords = ElementUtils.getPosition(parent.getContent());
                    x = _x - parentCoords[0];
                    y = _y - parentCoords[1];
                }
                
                org.wirez.core.api.command.Command<WiresCanvasHandler, CanvasCommandViolation> command = null;
                if ( element instanceof Node) {
                    
                    if ( null != parent ) {
                        command = canvasCommandFactory.ADD_CHILD_NODE( parent, (Node) element, factory );
                    } else {
                        command = canvasCommandFactory.ADD_NODE((Node) element, factory);
                    }

                } else if ( element instanceof Edge && null != parent ) {
                    command = canvasCommandFactory.ADD_EDGE( parent, (Edge) element, factory );
                } else {
                    throw new RuntimeException("Unrecognized element type for " + element);
                }

                // Execute both add element and move commands in batch, so undo will be done in batch as well.
                org.wirez.core.api.command.Command<WiresCanvasHandler, CanvasCommandViolation> moveCanvasElementCommand = 
                        canvasCommandFactory.UPDATE_POSITION(element, x ,y);
                
                // TODO: Use no rules.
                // canvasHandler.getCommandManager().execute( emptyRuleManager, command, moveCanvasElementCommand);
                canvasHandler.execute( command, moveCanvasElementCommand);
            }

            @Override
            public void onError(final ClientRuntimeError error) {
                showError(error);
            }
        });
       

    }

    private void log(final Level level, final String message) {
        if (LogConfiguration.loggingIsEnabled()) {
            LOGGER.log(level, message);
        }
    }
    
}
