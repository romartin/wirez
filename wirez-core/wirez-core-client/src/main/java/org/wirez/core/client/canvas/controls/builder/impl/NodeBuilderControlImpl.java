package org.wirez.core.client.canvas.controls.builder.impl;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandUtils;
import org.wirez.core.api.command.batch.BatchCommandResult;
import org.wirez.core.api.definition.adapter.DefinitionAdapter;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.graph.content.view.ViewConnector;
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
import org.wirez.core.client.canvas.event.CanvasProcessingCompletedEvent;
import org.wirez.core.client.canvas.event.CanvasProcessingStartedEvent;
import org.wirez.core.client.session.command.Session;
import org.wirez.core.client.shape.MutationContext;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.factory.ShapeFactory;
import org.wirez.core.client.shape.impl.AbstractConnector;
import org.wirez.core.client.util.ShapeUtils;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;

// TODO: Move visualization stuff to a view class.

@Dependent
public class NodeBuilderControlImpl extends AbstractCanvasHandlerControl implements NodeBuilderControl<AbstractCanvasHandler> {

    ClientDefinitionManager clientDefinitionManager;
    ShapeManager shapeManager;
    CanvasCommandFactory commandFactory;
    CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager;
    ElementBuilderControl<AbstractCanvasHandler> elementBuilderControl;
    Event<CanvasProcessingStartedEvent> canvasProcessingStartedEvent;
    Event<CanvasProcessingCompletedEvent> canvasProcessingCompletedEvent;

    @Inject
    public NodeBuilderControlImpl(final ClientDefinitionManager clientDefinitionManager, 
                                  final ShapeManager shapeManager, 
                                  final CanvasCommandFactory commandFactory, 
                                  final @Session CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager, 
                                  final @Element  ElementBuilderControl<AbstractCanvasHandler> elementBuilderControl, 
                                  final Event<CanvasProcessingStartedEvent> canvasProcessingStartedEvent, 
                                  final Event<CanvasProcessingCompletedEvent> canvasProcessingCompletedEvent) {
        this.clientDefinitionManager = clientDefinitionManager;
        this.shapeManager = shapeManager;
        this.commandFactory = commandFactory;
        this.canvasCommandManager = canvasCommandManager;
        this.elementBuilderControl = elementBuilderControl;
        this.canvasProcessingStartedEvent = canvasProcessingStartedEvent;
        this.canvasProcessingCompletedEvent = canvasProcessingCompletedEvent;
    }

    @Override
    public void enable(final AbstractCanvasHandler canvasHandler) {
        super.enable(canvasHandler);
        this.elementBuilderControl.enable( canvasHandler );
    }

    @Override
    public void disable() {
        super.disable();
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
    public void build(final NodeBuildRequest request) {

        final double x = request.getX();
        final double y = request.getY();
        final Node<View<?>, Edge> node = request.getNode();
        final Edge<View<?>, Node> inEdge = request.getInEdge();
        
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
                public void onComplete(final List<Command<AbstractCanvasHandler, CanvasViolation>> commands) {
                    
                    commandList.addAll( commands );
                    
                    if ( inEdge != null ) {

                        final Object edgeDef = inEdge.getContent().getDefinition();
                        final DefinitionAdapter<Object> edgeAdapter = clientDefinitionManager.getDefinitionAdapter( edgeDef.getClass() );
                        final String edgeId = edgeAdapter.getId( edgeDef );
                        final ShapeFactory<? ,?, ?> edgeFactory = shapeManager.getFactory( edgeId );
                        
                        commandList.add( commandFactory.ADD_EDGE( inEdge.getSourceNode() , inEdge, edgeFactory) );
                        commandList.add( commandFactory.SET_SOURCE_NODE( inEdge.getSourceNode(), inEdge, 0 ) );
                        commandList.add( commandFactory.SET_TARGET_NODE( node, inEdge, 0 ) );
                        
                    }

                    for ( final Command<AbstractCanvasHandler, CanvasViolation> command : commandList ) {
                        canvasCommandManager.batch( command );
                    }

                    BatchCommandResult<CanvasViolation> results = canvasCommandManager.executeBatch( canvasHandler );
                    
                    if ( !CommandUtils.isError( results ) ) {

                        updateEdgeMagnets( inEdge );
                        
                    }

                    fireProcessingCompleted();

                }
            });
            
            
        }
        
    }
    
    protected void updateEdgeMagnets( final Edge<View<?>, Node> inEdge ) {

        if ( null != inEdge && ( inEdge.getContent() instanceof  ViewConnector ) ) {

            final ViewConnector connectorContent = (ViewConnector) inEdge.getContent();

            final AbstractConnector edgeShape = (AbstractConnector) canvasHandler.getCanvas().getShape( inEdge.getUUID() );
            final Node source = inEdge.getSourceNode();
            final Node target = inEdge.getTargetNode();
            if ( null != source && null != target ) {
                final Shape<?> sShape = canvasHandler.getCanvas().getShape( source.getUUID() );
                final Shape<?> tShape = canvasHandler.getCanvas().getShape( target.getUUID() );
                final int[] magnetIndexes = ShapeUtils.getDefaultMagnetsIndex( sShape, tShape );
                connectorContent.setSourceMagnetIndex( magnetIndexes[0] );
                connectorContent.setTargetMagnetIndex( magnetIndexes[1] );
                edgeShape.applyConnections( inEdge, canvasHandler, MutationContext.STATIC );
            }

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
