package org.wirez.client.widgets.canvas;

import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import org.uberfire.client.workbench.widgets.common.ErrorPopupPresenter;
import org.uberfire.mvp.Command;
import org.wirez.client.widgets.event.AddShapeToCanvasEvent;
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
import org.wirez.core.api.util.UUID;
import org.wirez.core.api.util.WirezLogger;
import org.wirez.core.client.ClientDefinitionManager;
import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.CanvasViolation;
import org.wirez.core.client.canvas.controls.connection.ConnectionAcceptorControl;
import org.wirez.core.client.canvas.controls.containment.ContainmentAcceptorControl;
import org.wirez.core.client.canvas.controls.drag.DragControl;
import org.wirez.core.client.canvas.controls.select.SelectionControl;
import org.wirez.core.client.canvas.controls.toolbox.ToolboxControl;
import org.wirez.core.client.canvas.wires.WiresCanvas;
import org.wirez.core.client.canvas.wires.WiresCanvasHandler;
import org.wirez.core.client.service.ClientDiagramServices;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.service.ServiceCallback;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.factory.ShapeFactory;
import org.wirez.core.client.util.CanvasHighlightVisitor;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.uberfire.commons.validation.PortablePreconditions.checkNotNull;

@Dependent
public class WiresCanvasHandlerPresenter implements CanvasHandlerPresenter<Diagram, AbstractCanvasHandler> {
    
    private static Logger LOGGER = Logger.getLogger(WiresCanvasHandlerPresenter.class.getName());

    @Inject
    org.wirez.core.client.canvas.wires.WiresCanvas canvas;

    @Inject
    org.wirez.core.client.canvas.wires.WiresCanvasHandler<Diagram, WiresCanvas> canvasHandler;

    @Inject
    ConnectionAcceptorControl<WiresCanvasHandler> connectionAcceptorControl;
    
    @Inject
    ContainmentAcceptorControl<WiresCanvasHandler> containmentAcceptorControl;
    
    @Inject
    SelectionControl<AbstractCanvas, Shape> selectionControl;

    @Inject
    DragControl<AbstractCanvasHandler, Element> dragControl;

    @Inject
    ToolboxControl<AbstractCanvasHandler, Element> toolboxControl;

    @Inject
    ClientDefinitionManager clientDefinitionManager;

    @Inject
    ClientFactoryServices clientFactoryServices;

    @Inject
    ClientDiagramServices clientDiagramServices;

    @Inject
    @Empty
    EmptyRuleManager emptyRuleManager;

    @Inject
    ErrorPopupPresenter errorPopupPresenter;
    
    @Override
    public Widget asWidget() {
        return canvas.getView().asWidget();
    }


    @Override
    public void initialize(final int width, 
                           final int height) {

        // Create the canvas with a given size.
        canvas.initialize( width, height );

        // Initialize the canvas to handle.
        canvasHandler.initialize( canvas );

        // Enable canvas controls.
        selectionControl.enable( canvas );
        connectionAcceptorControl.enable( canvasHandler );
        containmentAcceptorControl.enable( canvasHandler );
        dragControl.enable( canvasHandler );
        toolboxControl.enable( canvasHandler );
        
    }

    @Override
    @SuppressWarnings("unchecked")
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
    public void load(final String diagramUUID,
                     final Command callback) {

        clientDiagramServices.get( diagramUUID, new ServiceCallback<Diagram>() {
            @Override
            public void onSuccess(final Diagram diagram) {
                open(diagram, callback);
            }

            @Override
            public void onError(final ClientRuntimeError error) {
                showError( error );
                callback.execute();
            }
        } );
        
    }

    @Override
    public void save(final Command callback) {
        
        clientDiagramServices.update(canvasHandler.getDiagram(), new ServiceCallback<Diagram>() {
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
    public void open(final Diagram diagram,
                     final Command callback) {

        // Draw the graph on the canvas.
        canvasHandler.draw( diagram );
        callback.execute();
        
    }

    @Override
    public void clear() {

        // Execute the clear canvas command.
        canvasHandler.execute( canvasHandler.getCommandFactory().CLEAR_CANVAS() );
        
    }
    
    public void clearSelection() {

        selectionControl.clearSelection();
                
    }

    @Override
    public void deleteSelected() {

        final Collection<Shape> selectedItems = selectionControl.getSelectedItems();
        if (selectedItems != null && !selectedItems.isEmpty()) {
            for (Shape shape : selectedItems) {
                Element element = canvasHandler.getGraphIndex().getNode(shape.getUUID());
                if (element == null) {
                    element = canvasHandler.getGraphIndex().getEdge(shape.getUUID());
                    if (element != null) {
                        log(Level.FINE, "Deleting edge with id " + element.getUUID());
                        canvasHandler.execute( canvasHandler.getCommandFactory().DELETE_EDGE( (Edge) element ));
                    }
                } else {
                    log(Level.FINE, "Deleting node with id " + element.getUUID());
                    canvasHandler.execute( canvasHandler.getCommandFactory().DELETE_NODE( (Node) element ));

                }
            }
        } else {
            log(Level.FINE, "Cannot delete element, no element selected on canvas.");
        }
        
    }

    @Override
    public void undo() {

        canvasHandler.undo();
        
    }

    /*
        PUBLIC UTILITY METHODS WHILE CODING
     */
    
    public void visitGraph() {
        new CanvasHighlightVisitor(canvasHandler).run();
    }

    public void resumeGraph() {
        WirezLogger.resume( canvasHandler.getDiagram().getGraph());
    }

    public void logGraph() {
        WirezLogger.log( canvasHandler.getDiagram().getGraph());
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
    
    @Override
    public Diagram getDiagram() {
        return canvasHandler.getDiagram();
    }

    @Override
    public AbstractCanvasHandler getCanvasHandler() {
        return canvasHandler;
    }

    private void showError(final ClientRuntimeError error) {
        final String message = error.getCause() != null ? error.getCause() : error.getMessage();
        showError(message);
    }

    private void showError(final Throwable throwable) {
        errorPopupPresenter.showMessage( throwable != null ? throwable.getMessage() : "Error");
    }

    private void showError(final String message) {
        log(Level.SEVERE, message );
        errorPopupPresenter.showMessage(message);
    }

    private void log(final Level level, final String message) {
        if (LogConfiguration.loggingIsEnabled()) {
            LOGGER.log(level, message);
        }
    }
   
}
