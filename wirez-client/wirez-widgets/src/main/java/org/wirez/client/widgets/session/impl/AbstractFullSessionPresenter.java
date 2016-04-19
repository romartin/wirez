package org.wirez.client.widgets.session.impl;

import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.Window;
import org.uberfire.client.workbench.widgets.common.ErrorPopupPresenter;
import org.uberfire.mvp.Command;
import org.wirez.client.widgets.event.AddShapeToCanvasEvent;
import org.wirez.client.widgets.session.FullSessionPresenter;
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
import org.wirez.core.api.util.UUID;
import org.wirez.core.api.util.WirezLogger;
import org.wirez.core.client.ClientDefinitionManager;
import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.canvas.command.CanvasViolation;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.controls.select.SelectionControl;
import org.wirez.core.client.service.ClientDiagramServices;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.service.ServiceCallback;
import org.wirez.core.client.session.CanvasFullSession;
import org.wirez.core.client.session.impl.DefaultCanvasSessionManager;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.factory.ShapeFactory;
import org.wirez.core.client.util.CanvasHighlightVisitor;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.uberfire.commons.validation.PortablePreconditions.checkNotNull;

public abstract class AbstractFullSessionPresenter<S extends CanvasFullSession<AbstractCanvas, AbstractCanvasHandler>> 
        extends AbstractReadOnlySessionPresenter<S>
        implements FullSessionPresenter<AbstractCanvas, AbstractCanvasHandler, S> {
    
    ClientDefinitionManager clientDefinitionManager;
    ClientFactoryServices clientFactoryServices;
    CanvasCommandFactory commandFactory;

    private static Logger LOGGER = Logger.getLogger(FullSessionPresenter.class.getName());
    
    @Inject
    public AbstractFullSessionPresenter(final DefaultCanvasSessionManager canvasSessionManager,
                                        final ClientDefinitionManager clientDefinitionManager,
                                        final ClientFactoryServices clientFactoryServices,
                                        final CanvasCommandFactory commandFactory,
                                        final ClientDiagramServices clientDiagramServices,
                                        final ErrorPopupPresenter errorPopupPresenter) {
        super(canvasSessionManager, clientDiagramServices, errorPopupPresenter);
        this.commandFactory = commandFactory;
        this.clientDefinitionManager = clientDefinitionManager;
        this.clientFactoryServices = clientFactoryServices;
    }

    @Override
    public void initialize(final S session, 
                           final int width, 
                           final int height) {
        super.initialize(session, width, height);

        // Enable canvas controls.
        final AbstractCanvasHandler canvasHandler = getCanvasHandler();
        session.getConnectionAcceptorControl().enable( canvasHandler );
        session.getContainmentAcceptorControl().enable( canvasHandler );
        session.getDragControl().enable( canvasHandler );
        session.getToolboxControl().enable( canvasHandler );
    }

    @Override
    public void newDiagram(final String uuid, 
                           final String title, 
                           final String definitionSetId, 
                           final String shapeSetId, 
                           final Command callback) {

        clientFactoryServices.newGraph(UUID.uuid(), definitionSetId, new ServiceCallback<Graph>() {
            @Override
            public void onSuccess(final Graph graph) {
                final Settings diagramSettings = new SettingsImpl( title, definitionSetId, shapeSetId );
                Diagram<Graph, Settings> diagram = clientFactoryServices.newDiagram( UUID.uuid(), graph, diagramSettings );
                open(diagram, callback);
            }

            @Override
            public void onError(final ClientRuntimeError error) {
                showError(error);
                callback.execute();
            }
        });
        
    }

    @Override
    public void save(final Command callback) {

        clientDiagramServices.update(getCanvasHandler().getDiagram(), new ServiceCallback<Diagram>() {
            @Override
            public void onSuccess(final Diagram item) {
                Window.alert("Diagram saved successfully [UUID=" + item.getUUID() + "]");
                callback.execute();
            }

            @Override
            public void onError(final ClientRuntimeError error) {
                showError(error);
                callback.execute();
            }
        });
        
    }

    @Override
    public void clear() {

        // Execute the clear canvas command.
        session.getCanvasCommandManager().execute( getCanvasHandler(), commandFactory.CLEAR_CANVAS() );
        
    }

    @Override
    public void undo() {

        session.getCanvasCommandManager().undo( getCanvasHandler() );
        
    }

    @Override
    public void deleteSelected() {

        final CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager = session.getCanvasCommandManager();
        final SelectionControl<AbstractCanvas, Shape> selectionControl = session.getShapeSelectionControl();
        
        final Collection<Shape> selectedItems = selectionControl.getSelectedItems();
        if (selectedItems != null && !selectedItems.isEmpty()) {
            
            for (Shape shape : selectedItems) {
                Element element = getCanvasHandler().getGraphIndex().getNode(shape.getUUID());
                if (element == null) {
                    element = getCanvasHandler().getGraphIndex().getEdge(shape.getUUID());
                    if (element != null) {
                        log(Level.FINE, "Deleting edge with id " + element.getUUID());
                        canvasCommandManager.execute( getCanvasHandler(), commandFactory.DELETE_EDGE( (Edge) element ));
                    }
                } else {
                    log(Level.FINE, "Deleting node with id " + element.getUUID());
                    canvasCommandManager.execute( getCanvasHandler(), commandFactory.DELETE_NODE( (Node) element ));

                }
            }
            
        } else {
            
            log(Level.FINE, "Cannot delete element, no element selected on canvas.");
            
        }
        
    }
    
    /*
        PUBLIC UTILITY METHODS FOR CODING & TESTING
     */

    public void visitGraph() {
        new CanvasHighlightVisitor(getCanvasHandler()).run();
    }

    public void resumeGraph() {
        WirezLogger.resume( getCanvasHandler().getDiagram().getGraph());
    }

    public void logGraph() {
        WirezLogger.log( getCanvasHandler().getDiagram().getGraph());
    }

    // TODO: 
    // This event should only have to be processed if the canvas is currently in use, 
    // but it's being processes for all canvas, so any shape added from palette results into all existing canvas screens.
    protected void AddShapeToCanvasEvent(@Observes AddShapeToCanvasEvent addShapeToCanvasEvent) {
        checkNotNull("addShapeToPaletteEvent", addShapeToCanvasEvent);
        final ShapeFactory factory = addShapeToCanvasEvent.getShapeFactory();
        final Object definition = addShapeToCanvasEvent.getDefinition();
        final double x = addShapeToCanvasEvent.getX();
        final double y = addShapeToCanvasEvent.getY();
        final double cx = session.getCanvas().getAbsoluteX();
        final double cy = session.getCanvas().getAbsoluteY();
        buildShape(definition, factory, x - cx, y - cy);
        session.getCanvas().draw();
    }

    protected void buildShape(final Object definition, final ShapeFactory factory,
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
                    final GraphBoundsIndexer boundsIndexer = new GraphBoundsIndexer(getCanvasHandler().getDiagram().getGraph());
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
                        command = commandFactory.ADD_CHILD_NODE( parent, (Node) element, factory );
                    } else {
                        command = commandFactory.ADD_NODE((Node) element, factory);
                    }

                } else if ( element instanceof Edge && null != parent ) {
                    command = commandFactory.ADD_EDGE( parent, (Edge) element, factory );
                } else {
                    throw new RuntimeException("Unrecognized element type for " + element);
                }

                // Execute both add element and move commands in batch, so undo will be done in batch as well.
                org.wirez.core.api.command.Command<AbstractCanvasHandler, CanvasViolation> moveCanvasElementCommand =
                        commandFactory.UPDATE_POSITION(element, x ,y);

                // TODO: Use no rules.
                // canvasHandler.getCommandManager().execute( emptyRuleManager, command, moveCanvasElementCommand);
                session.getCanvasCommandManager()
                        .batch( command)
                        .batch( moveCanvasElementCommand )
                        .executeBatch( getCanvasHandler() );
            }

            @Override
            public void onError(final ClientRuntimeError error) {
                showError(error);
            }
        });

    }

    @Override
    protected void showError(String message) {
        log( Level.SEVERE, message);
        super.showError(message);
    }

    private void log(final Level level, final String message) {
        if (LogConfiguration.loggingIsEnabled()) {
            LOGGER.log(level, message);
        }
    }
    
}
