package org.wirez.core.client.canvas.controls.builder.impl;

import org.wirez.core.client.command.Session;
import org.wirez.core.client.command.SessionCommandManager;
import org.wirez.core.client.shape.MutationContext;
import org.wirez.core.command.CommandManager;
import org.wirez.core.command.CommandResult;
import org.wirez.core.command.CommandUtils;
import org.wirez.core.command.batch.BatchCommandResult;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.client.api.ClientDefinitionManager;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.command.CanvasCommandManager;
import org.wirez.core.client.command.CanvasViolation;
import org.wirez.core.client.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.controls.AbstractCanvasHandlerControl;
import org.wirez.core.client.canvas.controls.builder.EdgeBuilderControl;
import org.wirez.core.client.canvas.controls.builder.request.EdgeBuildRequest;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.factory.ShapeFactory;
import org.wirez.core.client.shape.util.EdgeMagnetsHelper;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

@Dependent
public class EdgeBuilderControlImpl extends AbstractCanvasHandlerControl implements EdgeBuilderControl<AbstractCanvasHandler> {

    private static Logger LOGGER = Logger.getLogger(EdgeBuilderControlImpl.class.getName());

    ClientDefinitionManager clientDefinitionManager;
    ShapeManager shapeManager;
    CanvasCommandFactory commandFactory;
    CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager;
    EdgeMagnetsHelper magnetsHelper;

    @Inject
    public EdgeBuilderControlImpl(final ClientDefinitionManager clientDefinitionManager, 
                                  final ShapeManager shapeManager, 
                                  final CanvasCommandFactory commandFactory, 
                                  final @Session CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager,
                                  final EdgeMagnetsHelper magnetsHelper) {
        this.clientDefinitionManager = clientDefinitionManager;
        this.shapeManager = shapeManager;
        this.commandFactory = commandFactory;
        this.canvasCommandManager = canvasCommandManager;
        this.magnetsHelper = magnetsHelper;
    }

    @Override
    public boolean allows(final EdgeBuildRequest request) {

        final double x = request.getX();
        final double y = request.getY();
        final Edge<View<?>, Node> edge = request.getEdge();
        final AbstractCanvasHandler<?, ?> wch = canvasHandler;
        final Node<View<?>, Edge> inNode = request.getInNode();
        final Node<View<?>, Edge> outNode = request.getOutNode();

        boolean allowsSourceConn = true;
        if ( null != inNode ) {
            final CommandResult<CanvasViolation> cr1 = canvasCommandManager.allow( wch, commandFactory.SET_SOURCE_NODE((Node<? extends View<?>, Edge>) inNode, edge, 0) );
            allowsSourceConn = isAllowed( cr1 );
        }

        boolean allowsTargetConn = true;
        if ( null != outNode ) {
            final CommandResult<CanvasViolation> cr2 = canvasCommandManager.allow( wch, commandFactory.SET_TARGET_NODE((Node<? extends View<?>, Edge>) outNode, edge, 0) );
            allowsTargetConn = isAllowed( cr2 );
        }

        return allowsSourceConn & allowsTargetConn;
        
    }

    @Override
    @SuppressWarnings("unchecked")
    public void build( final EdgeBuildRequest request,
                       final BuildCallback buildCallback ) {

        final double x = request.getX();
        final double y = request.getY();
        final Edge<View<?>, Node> edge = request.getEdge();
        final AbstractCanvasHandler<?, ?> wch = canvasHandler;
        final Node<View<?>, Edge> inNode = request.getInNode();
        final Node<View<?>, Edge> outNode = request.getOutNode();
        final Canvas canvas = canvasHandler.getCanvas();

        if ( null == inNode ) {
            
            throw new RuntimeException(" An edge must be into the outgoing edges list from a node." );
            
        }

        final Shape sourceShape = canvas.getShape(inNode.getUUID());
        final Shape targetShape = outNode != null ? canvas.getShape(outNode.getUUID()) : null;

        int[] magnetIndexes = new int[] { 0, 0 };
        if ( outNode != null ) {
            
            magnetIndexes = magnetsHelper.getDefaultMagnetsIndex( sourceShape.getShapeView(), 
                    targetShape.getShapeView() );

        }

        final Object edgeDef = edge.getContent().getDefinition();
        final String edgeDefId = clientDefinitionManager.adapters().forDefinition().getId( edgeDef );
        final ShapeFactory factory = shapeManager.getFactory( edgeDefId );
        
        canvasCommandManager
                .batch( commandFactory.ADD_EDGE( inNode, edge, factory) )
                .batch( commandFactory.SET_SOURCE_NODE((Node<? extends View<?>, Edge>) inNode, edge, magnetIndexes[0]) );
        
        if ( null != outNode ) {
            
            canvasCommandManager.batch( commandFactory.SET_TARGET_NODE((Node<? extends View<?>, Edge>) outNode, edge, magnetIndexes[1]) );
            
        }

        final BatchCommandResult<CanvasViolation> results = canvasCommandManager.executeBatch( wch );

        if (CommandUtils.isError( results ) ) {
            
            LOGGER.log( Level.SEVERE, results.toString() );
            
        }

        canvasHandler.applyElementMutation( edge, MutationContext.STATIC );

        buildCallback.onSuccess( edge.getUUID() );

    }

    @Override
    protected void doDisable() {
        
    }

    private boolean isAllowed(CommandResult<CanvasViolation> result ) {
        return !CommandResult.Type.ERROR.equals( result.getType() );
    }
    
}
