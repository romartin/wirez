package org.wirez.core.client.canvas.controls.builder.impl;

import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.command.Command;
import org.wirez.core.command.CommandUtils;
import org.wirez.core.command.batch.BatchCommandResult;
import org.wirez.core.definition.adapter.DefinitionAdapter;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.graph.content.view.ViewConnector;
import org.wirez.core.client.ClientDefinitionManager;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.canvas.command.CanvasViolation;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.controls.AbstractCanvasHandlerControl;
import org.wirez.core.client.canvas.controls.builder.ElementBuilderControl;
import org.wirez.core.client.canvas.controls.builder.NodeBuilderControl;
import org.wirez.core.client.canvas.controls.builder.request.ElementBuildRequest;
import org.wirez.core.client.canvas.controls.builder.request.ElementBuildRequestImpl;
import org.wirez.core.client.canvas.controls.builder.request.NodeBuildRequest;
import org.wirez.core.client.canvas.event.processing.CanvasProcessingCompletedEvent;
import org.wirez.core.client.canvas.event.processing.CanvasProcessingStartedEvent;
import org.wirez.core.client.session.command.Session;
import org.wirez.core.client.shape.EdgeShape;
import org.wirez.core.client.shape.MutationContext;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.factory.ShapeFactory;
import org.wirez.core.client.shape.util.EdgeMagnetsHelper;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;

@Dependent
public class NodeBuilderControlImpl extends AbstractCanvasHandlerControl implements NodeBuilderControl<AbstractCanvasHandler> {

    ClientDefinitionManager clientDefinitionManager;
    ShapeManager shapeManager;
    CanvasCommandFactory commandFactory;
    CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager;
    ElementBuilderControl<AbstractCanvasHandler> elementBuilderControl;
    Event<CanvasProcessingStartedEvent> canvasProcessingStartedEvent;
    Event<CanvasProcessingCompletedEvent> canvasProcessingCompletedEvent;
    EdgeMagnetsHelper magnetsHelper;

    @Inject
    public NodeBuilderControlImpl(final ClientDefinitionManager clientDefinitionManager, 
                                  final ShapeManager shapeManager, 
                                  final CanvasCommandFactory commandFactory, 
                                  final @Session CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager, 
                                  final @Element  ElementBuilderControl<AbstractCanvasHandler> elementBuilderControl, 
                                  final Event<CanvasProcessingStartedEvent> canvasProcessingStartedEvent, 
                                  final Event<CanvasProcessingCompletedEvent> canvasProcessingCompletedEvent,
                                  final EdgeMagnetsHelper magnetsHelper) {
        this.clientDefinitionManager = clientDefinitionManager;
        this.shapeManager = shapeManager;
        this.commandFactory = commandFactory;
        this.canvasCommandManager = canvasCommandManager;
        this.elementBuilderControl = elementBuilderControl;
        this.canvasProcessingStartedEvent = canvasProcessingStartedEvent;
        this.canvasProcessingCompletedEvent = canvasProcessingCompletedEvent;
        this.magnetsHelper = magnetsHelper;
    }

    @Override
    public void enable(final AbstractCanvasHandler canvasHandler) {
        super.enable(canvasHandler);
        this.elementBuilderControl.enable( canvasHandler );
    }

    @Override
    protected void doDisable() {
        this.elementBuilderControl.disable();
    }

    @Override
    public boolean allows(final NodeBuildRequest request) {
        
        final double x = request.getX();
        final double y = request.getY();
        final Node<View<?>, Edge> node = request.getNode();
        
        if ( null != node ) {
            
            final ElementBuildRequest<AbstractCanvasHandler> request1 = 
                    new ElementBuildRequestImpl( x, y , node.getContent().getDefinition(), null );
            
            return elementBuilderControl.allows( request1 );
            
        }
        
        return false;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public void build( final NodeBuildRequest request,
                       final BuildCallback buildCallback ) {

        final double x = request.getX();
        final double y = request.getY();
        final Node<View<?>, Edge> node = request.getNode();
        final Edge<View<?>, Node> inEdge = request.getInEdge();
        final int sourceManget = request.getSourceManger();
        final int targetMagnet = request.getTargetMagnet();

        if ( null != node ) {
            
            fireProcessingStarted();
            
            final Object nodeDef = node.getContent().getDefinition();
            final DefinitionAdapter<Object> nodeAdapter = clientDefinitionManager.getDefinitionAdapter( nodeDef.getClass() );
            final String nodeId = nodeAdapter.getId( nodeDef );
            final ElementBuilderControlImpl ebc = getElementBuilderControl();
            final Node<View<?>, Edge> parent = ebc.getParent( x, y );
            final Double[] childCoordinates = ebc.getChildCoordinates( parent, x, y );
            final ShapeFactory<Object ,AbstractCanvasHandler, ?> nodeShapeFactory = shapeManager.getFactory( nodeId ); 
            
            final List<Command<AbstractCanvasHandler, CanvasViolation>> commandList = new LinkedList<>();
            ebc.getElementCommands(node, parent, nodeShapeFactory, childCoordinates[0], childCoordinates[1], new AbstractElementBuilderControl.CommandsCallback() {
                @Override
                public void onComplete( final String uuid,
                                        final List<Command<AbstractCanvasHandler, CanvasViolation>> commands) {
                    
                    commandList.addAll( commands );

                    if ( inEdge != null ) {

                        final Object edgeDef = inEdge.getContent().getDefinition();
                        final DefinitionAdapter<Object> edgeAdapter = clientDefinitionManager.getDefinitionAdapter( edgeDef.getClass() );
                        final String edgeId = edgeAdapter.getId( edgeDef );
                        final ShapeFactory<? ,?, ?> edgeFactory = shapeManager.getFactory( edgeId );

                        // The commands to batch for the edge that connects both nodes.
                        commandList.add( commandFactory.ADD_EDGE( inEdge.getSourceNode() , inEdge, edgeFactory) );
                        commandList.add( commandFactory.SET_SOURCE_NODE( inEdge.getSourceNode(), inEdge, sourceManget ) );
                        commandList.add( commandFactory.SET_TARGET_NODE( node, inEdge, targetMagnet ) );
                        
                    }

                    for ( final Command<AbstractCanvasHandler, CanvasViolation> command : commandList ) {

                        canvasCommandManager.batch( command );

                    }

                    BatchCommandResult<CanvasViolation> results = canvasCommandManager.executeBatch( canvasHandler );
                    
                    if ( !CommandUtils.isError( results ) ) {

                        updateConnectorShape( inEdge, node, sourceManget, targetMagnet );
                        
                    }

                    buildCallback.onSuccess( uuid );

                    fireProcessingCompleted();

                }

                @Override
                public void onError( final ClientRuntimeError error ) {

                    buildCallback.onError( error );

                    fireProcessingCompleted();

                }

            });

        }
        
    }

    @SuppressWarnings( "unchecked" )
    protected void updateConnectorShape( final Edge<View<?>, Node> inEdge,
                                      final Node targetNode,
                                      final int sourceMagnet,
                                      final int targetManget ) {

        final ViewConnector connectorContent = (ViewConnector) inEdge.getContent();

        canvasHandler.applyElementMutation( inEdge, MutationContext.STATIC );

        final EdgeShape edgeShape = (EdgeShape) canvasHandler.getCanvas().getShape( inEdge.getUUID() );

        final Node source = inEdge.getSourceNode();

        if ( null != source && null != targetNode ) {

            final Shape<?> sShape = canvasHandler.getCanvas().getShape( source.getUUID() );
            final Shape<?> tShape = canvasHandler.getCanvas().getShape( targetNode.getUUID() );

            connectorContent.setSourceMagnetIndex( sourceMagnet );
            connectorContent.setTargetMagnetIndex( targetManget );
            edgeShape.applyConnections( inEdge, sShape.getShapeView(), tShape.getShapeView(), MutationContext.STATIC );

        }

    }
    
    protected ElementBuilderControlImpl getElementBuilderControl() {
        return (ElementBuilderControlImpl) elementBuilderControl;
    }

    protected void fireProcessingStarted() {
        canvasProcessingStartedEvent.fire( new CanvasProcessingStartedEvent( canvasHandler ) );
    }

    protected void fireProcessingCompleted() {
        canvasProcessingCompletedEvent.fire( new CanvasProcessingCompletedEvent( canvasHandler ) );
    }
    
    
}
