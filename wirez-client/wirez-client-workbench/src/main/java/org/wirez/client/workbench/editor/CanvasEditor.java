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

package org.wirez.client.workbench.editor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.IsWidget;
import org.jboss.errai.bus.client.api.messaging.Message;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.ErrorCallback;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.uberfire.backend.vfs.ObservablePath;
import org.uberfire.backend.vfs.Path;
import org.uberfire.client.annotations.*;
import org.uberfire.client.mvp.PlaceManager;
import org.uberfire.client.workbench.events.ChangeTitleWidgetEvent;
import org.uberfire.client.workbench.widgets.common.ErrorPopupPresenter;
import org.uberfire.ext.editor.commons.client.BaseEditor;
import org.uberfire.ext.editor.commons.client.file.SaveOperationService;
import org.uberfire.ext.editor.commons.service.support.SupportsCopy;
import org.uberfire.ext.editor.commons.service.support.SupportsDelete;
import org.uberfire.lifecycle.*;
import org.uberfire.mvp.Command;
import org.uberfire.mvp.ParameterizedCommand;
import org.uberfire.mvp.PlaceRequest;
import org.uberfire.workbench.model.menu.Menus;
import org.wirez.client.widgets.canvas.Canvas;
import org.wirez.client.widgets.event.AddShapeToCanvasEvent;
import org.wirez.client.workbench.event.CanvasScreenStateChangedEvent;
import org.wirez.client.workbench.screens.CanvasScreen;
import org.wirez.client.workbench.screens.DefaultGraphType;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.graph.Bounds;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.factory.DefaultGraphFactory;
import org.wirez.core.api.graph.factory.ViewElementFactory;
import org.wirez.core.api.graph.impl.*;
import org.wirez.core.api.service.GraphVfsServices;
import org.wirez.core.api.util.Logger;
import org.wirez.core.api.util.UUID;
import org.wirez.core.client.Shape;
import org.wirez.core.client.ShapeSet;
import org.wirez.core.client.WirezClientManager;
import org.wirez.core.client.canvas.CanvasSettings;
import org.wirez.core.client.canvas.DefaultCanvasSettingsBuilder;
import org.wirez.core.client.canvas.control.SelectionManager;
import org.wirez.core.client.canvas.command.CanvasCommand;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.canvas.command.impl.DefaultCanvasCommands;
import org.wirez.core.client.canvas.impl.DefaultCanvasHandler;
import org.wirez.core.client.factory.ShapeFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.*;

import static org.uberfire.commons.validation.PortablePreconditions.checkNotNull;
import static org.uberfire.ext.editor.commons.client.menu.MenuItems.SAVE;

@Dependent
@WorkbenchEditor(identifier = CanvasEditor.SCREEN_ID, supportedTypes = {DefaultGraphType.class}, priority = Integer.MAX_VALUE)
public class CanvasEditor extends BaseEditor {

    public static final String SCREEN_ID = "CanvasEditor";

    @Inject
    Canvas canvas;

    @Inject
    DefaultCanvasHandler canvasHandler;

    @Inject
    WirezClientManager wirezClientManager;
    
    @Inject
    DefaultCanvasCommands defaultCanvasCommands;

    @Inject
    Caller<GraphVfsServices> services;
    
    @Inject
    DefaultGraphType resourceType;
    
    @Inject
    ErrorPopupPresenter errorPopupPresenter;
    
    @Inject
    PlaceManager placeManager;

    @Inject
    Event<ChangeTitleWidgetEvent> changeTitleNotificationEvent;

    @Inject
    Event<CanvasScreenStateChangedEvent> canvasScreenStateChangedEvent;

    @Inject
    CanvasEditorView view;
    
    private String title = "Canvas Screen";
    
    @PostConstruct
    public void init() {
        view.setWidget(canvas.asWidget());
        super.baseView = view;
    }

    @OnMayClose
    public boolean onMayClose() {
        return super.mayClose(getCurrentModelHash());
    }

    protected Caller<? extends SupportsDelete> getDeleteServiceCaller() {
        return services;
    }

    protected Caller<? extends SupportsCopy> getCopyServiceCaller() {
        return services;
    }
    
    @OnStartup
    public void onStartup(final ObservablePath path, final PlaceRequest place) {
        super.baseView = view;

        init(path,
                place,
                resourceType,
                true,
                false,
                SAVE);
        
        String uuid = place.getParameter( "uuid", "" );
        String wirezSetId = place.getParameter( "defSetId", "" );
        String shapeSetUUID = place.getParameter( "shapeSetId", "" );
        String title = place.getParameter( "title", "" );

        final DefinitionSet wirezSet = getDefinitionSet(wirezSetId);
        final ShapeSet shapeSet = getShapeSet(shapeSetUUID);
        final DefaultGraphFactory graphFactory = getGraphFactory(wirezSet);

        // For testing...
        final Map<String, Object> properties = new HashMap<String, Object>();
        final Bounds bounds = new DefaultBounds(
                new DefaultBound(0d,0d),
                new DefaultBound(0d,0d)
        );

        final Set<String> labels = new HashSet<>();
        final DefaultGraph graph = (DefaultGraph) graphFactory.build(uuid, labels, properties, bounds);

        open(uuid, wirezSet, shapeSet, title, graph);
        
    }

    private void open(String uuid, DefinitionSet definitionSet, ShapeSet shapeSet, String title, Graph graph) {

        CanvasEditor.this.title = title;
        changeTitleNotificationEvent.fire(new ChangeTitleWidgetEvent(place, this.title));
        
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
        return menus;
    }

    private void setStateActive() {
        canvasScreenStateChangedEvent.fire(new CanvasScreenStateChangedEvent(canvasHandler, CanvasScreen.CanvasScreenState.ACTIVE));
    }

    private void setStateNotActive() {
        canvasScreenStateChangedEvent.fire(new CanvasScreenStateChangedEvent(canvasHandler, CanvasScreen.CanvasScreenState.NOT_ACTIVE));
    }
    
    protected void makeMenuBar() {
        super.makeMenuBar();
        menuBuilder
                .addCommand("Clear grid", getClearGridCommand())
                .addCommand("Clear selection", getClearSelectionCommand())
                .addCommand("Delete selected", getDeleteSelectionCommand())
                .addCommand("Undo", getUndoCommand())
                .addCommand("Log graph", getLogGraphCommand())
                .addCommand("Run graph", getRunGraphCommand());
        
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
                                canvasHandler.execute(defaultCanvasCommands.DELETE_EDGE((ViewEdge) element));
                            }
                        } else {
                            GWT.log("Deleting node with id " + element.getUUID());
                            canvasHandler.execute(defaultCanvasCommands.DELETE_NODE((ViewNode) element));

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

    private Command getRunGraphCommand() {
        return new Command() {
            public void execute() {
                runGraph();
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
            CanvasEditor.this.showError(throwable);
            return false;
        }
    };
    private void undo() {
        ((CanvasCommandManager)canvasHandler).undo();
    }

    private void logGraph() {
        Logger.resume((DefaultGraph) canvasHandler.getGraph());
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

    @WorkbenchPartTitleDecoration
    public IsWidget getTitle() {
        return super.getTitle();
    }

    @WorkbenchPartTitle
    public String getTitleText() {
        return title;
    }

    @Override
    protected void save() {
        new SaveOperationService().save(versionRecordManager.getCurrentPath(),
                new ParameterizedCommand<String>() {
                    @Override public void execute(final String commitMessage) {
                        final DefaultGraph graph = canvasHandler.getGraph();
                        services.call(new RemoteCallback<Path>() {
                            @Override
                            public void callback(final Path path) {
                                CanvasEditor.this.getSaveSuccessCallback(getCurrentModelHash()).callback(path);
                                placeManager.closePlace(CanvasEditor.this.place);
                            }
                        }, errorCallback)
                                .save(graph, commitMessage);

                    }
                }
        );
        concurrentUpdateSessionInfo = null;
    }

    public int getCurrentModelHash() {
        final DefaultGraph graph = canvasHandler.getGraph();
        if (graph == null) return 0;
        return graph.getUUID().hashCode();
    }

    @Override
    protected void loadContent() {
        services.call(loadCallback, errorCallback).get(versionRecordManager.getCurrentPath());
    }

    RemoteCallback<DefaultGraph> loadCallback = new RemoteCallback<DefaultGraph>() {
        public void callback(final DefaultGraph result) {
            GWT.log("Graph loaded = " + result.toString());
        }
    };

    @WorkbenchPartView
    public IsWidget getWidget() {
        return view.asWidget();
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
                            final double x, final double y) {
        final Element element = buildElement(definition, x, y);

        CanvasCommand command = null;
        if ( element instanceof ViewNode) {
            command = defaultCanvasCommands.ADD_NODE((ViewNode) element, factory);
        } else if ( element instanceof ViewEdge) {
            command = defaultCanvasCommands.ADD_EDGE((ViewEdge) element, factory);
        }

        if ( null != command ) {
            canvasHandler.execute(command);
        }

    }

    private ViewElement<? extends Definition> buildElement(final Definition wirez, final double _x, final double _y) {

        final double x = _x > -1 ? _x : 100d;
        final double y = _y > -1 ? _y : 100d;

        final Bounds bounds =
                new DefaultBounds(
                        // TODO: Size hardcoded by default to 50...
                        new DefaultBound(x + 50, y + 50),
                        new DefaultBound(x, y)
                );
        return ((ViewElementFactory) wirez).build(UUID.uuid(), new HashSet<String>(), new HashMap<String, Object>(), bounds);

    }

}
