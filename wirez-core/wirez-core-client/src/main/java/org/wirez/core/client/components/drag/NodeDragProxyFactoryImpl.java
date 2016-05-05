package org.wirez.core.client.components.drag;

import com.ait.lienzo.client.core.shape.wires.WiresShape;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.graph.content.view.ViewConnector;
import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.shape.MutationContext;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.factory.ShapeFactory;
import org.wirez.core.client.shape.impl.AbstractConnector;
import org.wirez.core.client.shape.impl.AbstractShape;
import org.wirez.core.client.util.ShapeUtils;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

// TODO: Move visualization logic to a view class.

@Dependent
public class NodeDragProxyFactoryImpl implements NodeDragProxyFactory<AbstractCanvasHandler> {
    
    private AbstractCanvasHandler canvasHandler;

    ShapeDragProxyFactory<AbstractCanvas> shapeDragProxyFactory;

    @Inject
    public NodeDragProxyFactoryImpl(ShapeDragProxyFactory<AbstractCanvas> shapeDragProxyFactory) {
        this.shapeDragProxyFactory = shapeDragProxyFactory;
    }

    @Override
    public DragProxyFactory<AbstractCanvasHandler, Item, DragProxyCallback> proxyFor(final AbstractCanvasHandler context) {
        this.canvasHandler = context;
        this.shapeDragProxyFactory.proxyFor( context.getCanvas() );
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public DragProxyFactory<AbstractCanvasHandler, Item, DragProxyCallback> newInstance(final Item item, 
                                                                                        final int x, 
                                                                                        final int y, 
                                                                                        final DragProxyCallback callback) {

        final AbstractCanvas canvas = canvasHandler.getCanvas();

        final Node<View<?>, Edge> node = item.getNode();

        final ShapeFactory<Object, AbstractCanvasHandler, ?> nodeShapeFactory = item.getNodeShapeFactory();

        final Edge<View<?>, Node> inEdge = item.getInEdge();

        final Node<View<?>, Edge> inEdgeSourceNode = item.getInEdgeSourceNode();
        
        final ShapeFactory<Object, AbstractCanvasHandler, ?> edgeShapeFactory = item.getInEdgeShapeFactory();

        final AbstractShape nodeShape = (AbstractShape) nodeShapeFactory.build( node.getContent().getDefinition(), canvasHandler );
        nodeShape.applyProperties( node, MutationContext.STATIC );
        ;
        final AbstractConnector edgeShape = (AbstractConnector) edgeShapeFactory.build( inEdge.getContent().getDefinition(), canvasHandler );
        canvas.addTransientShape( edgeShape );
        edgeShape.applyProperties( inEdge, MutationContext.STATIC );
        
        final Shape<?> edgeSourceNodeShape = canvasHandler.getCanvas().getShape( inEdgeSourceNode.getUUID() );

        shapeDragProxyFactory.newInstance( nodeShape, x, y, new DragProxyCallback() {
            
            @Override
            public void onStart(final int x, final int y) {

                callback.onStart( x , y );

                drawEdge();
                
            }

            @Override
            public void onMove(final int x,
                               final int y) {

                callback.onMove( x , y );
                
                drawEdge();

            }

            @Override
            public void onComplete(final int x,
                                   final int y) {

                callback.onComplete( x ,y );
                
                canvas.deleteTransientShape( edgeShape );

                canvas.draw();

            }
            
            private void drawEdge() {

                if ( inEdge.getContent() instanceof ViewConnector) {

                    final ViewConnector viewConnector = (ViewConnector) inEdge.getContent();
                    final int[] magnetIndexes = ShapeUtils.getDefaultMagnetsIndex( edgeSourceNodeShape, nodeShape );
                    viewConnector.setSourceMagnetIndex( magnetIndexes[0] );
                    viewConnector.setTargetMagnetIndex( magnetIndexes[1] );

                }
                
                edgeShape.applyConnections( inEdge, edgeSourceNodeShape.getShapeView(), nodeShape.getShapeView(), MutationContext.STATIC );
                canvas.draw();
                
            }
            
        });
        
        return this;
    }
    
}
