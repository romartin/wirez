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

import com.ait.lienzo.client.core.Context2D;
import com.ait.lienzo.client.core.event.NodeMouseMoveEvent;
import com.ait.lienzo.client.core.event.NodeMouseMoveHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.Window;
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
import org.wirez.client.widgets.event.AddShapeToCanvasEvent;
import org.wirez.client.workbench.event.CanvasScreenStateChangedEvent;
import org.wirez.core.api.definition.adapter.DefinitionAdapter;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.diagram.Settings;
import org.wirez.core.api.diagram.SettingsImpl;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.graph.processing.util.GraphBoundsIndexer;
import org.wirez.core.api.graph.util.GraphUtils;
import org.wirez.core.api.rule.Empty;
import org.wirez.core.api.rule.EmptyRuleManager;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.api.util.UUID;
import org.wirez.core.api.util.WirezLogger;
import org.wirez.core.client.ClientDefinitionManager;
import org.wirez.core.client.Shape;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.CanvasViolation;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.control.SelectionManager;
import org.wirez.core.client.canvas.lienzo.LienzoLayer;
import org.wirez.core.client.canvas.wires.WiresCanvas;
import org.wirez.core.client.canvas.wires.WiresCanvasHandler;
import org.wirez.core.client.factory.ShapeFactory;
import org.wirez.core.client.service.ClientDiagramServices;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.service.ServiceCallback;
import org.wirez.core.client.util.CanvasHighlightVisitor;

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

    private static Logger LOGGER = Logger.getLogger(CanvasScreen.class.getName());
    
    public static final String SCREEN_ID = "CanvasScreen";

    public enum CanvasScreenState {
        ACTIVE, NOT_ACTIVE;
    }

    @Inject
    WiresCanvas canvas;

    @Inject
    WiresCanvasHandler<Diagram, WiresCanvas> canvasHandler;
    
    @Inject
    ClientDefinitionManager clientDefinitionManager;
    
    @Inject
    ClientFactoryServices clientFactoryServices;
    
    @Inject
    ClientDiagramServices clientDiagramServices;
    
    @Inject
    ErrorPopupPresenter errorPopupPresenter;

    @Inject
    PlaceManager placeManager;
    
    @Inject
    @Empty
    EmptyRuleManager emptyRuleManager;

    @Inject
    Event<ChangeTitleWidgetEvent> changeTitleNotificationEvent;

    @Inject
    Event<CanvasScreenStateChangedEvent> canvasScreenStateChangedEvent;

    private Menus menu = null;
    private PlaceRequest placeRequest;
    private String title = "Canvas Screen";

    private HandlerRegistration mousePointerCoordsHandlerReg;
    private boolean isStateSaved = false;
    
    @PostConstruct
    public void init() {

    }

    @OnStartup
    public void onStartup(final PlaceRequest placeRequest) {

        this.placeRequest = placeRequest;
        final String _uuid = placeRequest.getParameter( "uuid", "" );

        CanvasScreen.this.menu = makeMenuBar();
        
        final boolean isCreate = _uuid == null || _uuid.trim().length() == 0;
        
        if (isCreate) {

            final String defSetId = placeRequest.getParameter( "defSetId", "" );
            final String shapeSetd = placeRequest.getParameter( "shapeSetId", "" );
            final String title = placeRequest.getParameter( "title", "" );
            
            
            clientFactoryServices.newGraph(UUID.uuid(), defSetId, new ServiceCallback<Graph>() {
                @Override
                public void onSuccess(final Graph graph) {
                    final Settings diagramSettings = new SettingsImpl( title, defSetId, shapeSetd );
                    Diagram<Graph, Settings> diagram = clientFactoryServices.newDiagram( UUID.uuid(), graph, diagramSettings );
                    open(diagram);
                }

                @Override
                public void onError(final ClientRuntimeError error) {
                    showError(error);
                }
            });

        } else {
         
            clientDiagramServices.get(_uuid, new ServiceCallback<Diagram>() {
                @Override
                public void onSuccess(final Diagram diagram) {
                    open(diagram);
                }

                @Override
                public void onError(final ClientRuntimeError error) {
                    showError( error );
                }
            });
            
        }
        
    }

    private void open(final Diagram diagram) {

        final String title = diagram.getSettings().getTitle();
        
        // Create the canvas with a fixed size.
        canvas.initialize( 1000, 1000 );
        
        // Draw the graph on the canvas.
        canvasHandler.draw(diagram, canvas);

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
                .newTopLevelMenu("Mouse pointer coords")
                .respondsWith(getMousePointerCoordsCommand())
                .endMenu()
                .newTopLevelMenu("Clear grid")
                .respondsWith(getClearGridCommand())
                .endMenu()
                .newTopLevelMenu("Undo")
                .respondsWith(getUndoCommand())
                .endMenu()
                .newTopLevelMenu("Resume graph")
                .respondsWith(getResumeGraphCommand())
                .endMenu()
                .newTopLevelMenu("Visit graph")
                .respondsWith(getVisitGraphCommand())
                .endMenu()
                .newTopLevelMenu("Save")
                .respondsWith(getSaveCommand())
                .endMenu()
                .build();
    }

    private Command getSaveCommand() {
        return () -> clientDiagramServices.update(canvasHandler.getDiagram(), new ServiceCallback<Diagram>() {
            @Override
            public void onSuccess(final Diagram item) {
                Window.alert("Diagram saved successfully");                
            }

            @Override
            public void onError(final ClientRuntimeError error) {
                showError(error);
            }
        });
    }
    

    private Command getMousePointerCoordsCommand() {
        return new Command() {
            public void execute() {

                if ( null == mousePointerCoordsHandlerReg ) {
                    final LienzoLayer wiresLayer = (LienzoLayer) canvasHandler.getCanvas().getLayer();
                    mousePointerCoordsHandlerReg = wiresLayer.getLienzoLayer().addNodeMouseMoveHandler(new NodeMouseMoveHandler() {
                        @Override
                        public void onNodeMouseMove(NodeMouseMoveEvent nodeMouseMoveEvent) {
                            LOGGER.log(Level.INFO, "Mouse at [" + nodeMouseMoveEvent.getX() + ", " + nodeMouseMoveEvent.getY() + "]");
                            /*final GraphBoundsIndexer indexer = new GraphBoundsIndexer(canvasHandler.getDiagram().getGraph());
                            final Node node = indexer.getNodeAt(nodeMouseMoveEvent.getX(), nodeMouseMoveEvent.getY());
                            LOGGER.log(Level.INFO, "Node [" + ( node != null ? node.getUUID() : null ) +
                                    "at [" + nodeMouseMoveEvent.getX() + ", " + nodeMouseMoveEvent.getY() + "]");*/
                        }
                    });
                    
                } else {
                    mousePointerCoordsHandlerReg.removeHandler();
                    mousePointerCoordsHandlerReg = null;
                    
                }

            }
        };
    }

    private Command getSwitchLogLevelCommand() {
        return () -> {
            final Level level = Logger.getLogger("").getLevel();
            final Level newLevel = Level.SEVERE.equals(level) ? Level.FINE : Level.SEVERE;
            LOGGER.log(Level.SEVERE, "Switching to log level [" + newLevel.getName() + "]");
            Logger.getLogger("").setLevel(newLevel);
        };
    }

    private Command getSaveRestoreStateCommand() {
        return () -> {
            LienzoLayer wiresLayer = (LienzoLayer) canvas.getLayer();
            Context2D context = wiresLayer.getLienzoLayer().getContext();
            if ( isStateSaved ) {
                context.restore();
                GWT.log("State restored");
                isStateSaved = false;
            } else {
                context.save();
                GWT.log("State saved");
                isStateSaved = true;
            }
        };
    }

    private Command getClearGridCommand() {
        return () -> CanvasScreen.this.execute( canvasHandler.getCommandFactory().CLEAR_CANVAS() );
    }

    private Command getClearSelectionCommand() {
        return () -> ((SelectionManager)canvas).clearSelection();
    }

    private Command getDeleteSelectionCommand() {
        return () -> {
            final Collection<Shape> selectedItems = ((SelectionManager)canvas).getSelectedItems();
            if (selectedItems != null && !selectedItems.isEmpty()) {
                for (Shape shape : selectedItems) {
                    Element element = canvasHandler.getGraphIndex().getNode(shape.getId());
                    if (element == null) {
                        element = canvasHandler.getGraphIndex().getEdge(shape.getId());
                        if (element != null) {
                            log(Level.FINE, "Deleting edge with id " + element.getUUID());
                            CanvasScreen.this.execute( canvasHandler.getCommandFactory().DELETE_EDGE( (Edge) element ));
                        }
                    } else {
                        log(Level.FINE, "Deleting node with id " + element.getUUID());
                        CanvasScreen.this.execute( canvasHandler.getCommandFactory().DELETE_NODE( (Node) element ));

                    }
                }
            } else {
                log(Level.FINE, "Cannot delete element, no element selected on canvas.");
            }
        };
    }
    
    private Command getUndoCommand() {
        return () -> undo();
    }

    private Command getLogGraphCommand() {
        return () -> logGraph();
    }

    private Command getResumeGraphCommand() {
        return () -> resumeGraph();
    }
    
    private Command getVisitGraphCommand() {
        return () -> visitGraph();
    }

    private interface DefinitionSetRequestCallback {
        void onSuccess ( Object definitionSet );
    }
    
    

    private final ErrorCallback<Message> errorCallback = new ErrorCallback<Message>() {
        @Override
        public boolean error(final Message message, final Throwable throwable) {
            CanvasScreen.this.showError(throwable);
            return false;
        }
    };
    
    private void execute(final org.wirez.core.api.command.Command<AbstractCanvasHandler, CanvasViolation> command) {
        canvasHandler.execute( command );
    }
    
    private void undo() {
        canvasHandler.undo( );
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
        return canvas.getView();
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
        final Object definition = addShapeToCanvasEvent.getDefinition();
        final double x = addShapeToCanvasEvent.getX();
        final double y = addShapeToCanvasEvent.getY();
        final double cx = canvas.getAbsoluteX();
        final double cy = canvas.getAbsoluteY();
        buildShape(definition, factory, x - cx, y - cy);
        canvas.draw();
    }

    private void buildShape(final Object definition, final ShapeFactory factory,
                            final double _x, final double _y) {
        
        final DefinitionAdapter definitionAdapter = clientDefinitionManager.getDefinitionAdapter( definition. getClass() );
        final String defId = definitionAdapter.getId( definition );
        clientFactoryServices.newElement(org.wirez.core.api.util.UUID.uuid(), defId, new ServiceCallback<Element>() {
            @Override
            public void onSuccess(final Element element) {
                double x = _x > -1 ? _x : 100d;
                double y = _y > -1 ? _y : 100d;

                Node<View<?>, Edge> parent = null;
                if ( _x > -1 && _y > -1) {
                    final GraphBoundsIndexer boundsIndexer = new GraphBoundsIndexer(canvasHandler.getDiagram().getGraph());
                    parent = boundsIndexer.getNodeAt(_x, _y);
                    if ( null != parent) {
                        final Double[] parentCoords = GraphUtils.getPosition(parent.getContent());
                        x = _x - parentCoords[0];
                        y = _y - parentCoords[1];
                    }
                }

                org.wirez.core.api.command.Command<AbstractCanvasHandler, CanvasViolation> command = null;
                if ( element instanceof Node) {

                    if ( null != parent ) {
                        command = canvasHandler.getCommandFactory().ADD_CHILD_NODE( parent, (Node) element, factory );
                    } else {
                        command = canvasHandler.getCommandFactory().ADD_NODE((Node) element, factory);
                    }

                } else if ( element instanceof Edge && null != parent ) {
                    command = canvasHandler.getCommandFactory().ADD_EDGE( parent, (Edge) element, factory );
                } else {
                    throw new RuntimeException("Unrecognized element type for " + element);
                }

                // Execute both add element and move commands in batch, so undo will be done in batch as well.
                org.wirez.core.api.command.Command<AbstractCanvasHandler, CanvasViolation> moveCanvasElementCommand =
                        canvasHandler.getCommandFactory().UPDATE_POSITION(element, x ,y);

                // TODO: Use no rules.
                // canvasHandler.getCommandManager().execute( emptyRuleManager, command, moveCanvasElementCommand);
                canvasHandler
                        .batch( command)
                        .batch( moveCanvasElementCommand )
                        .executeBatch();
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
