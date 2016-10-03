package org.kie.workbench.common.stunner.core.client.components.drag;

import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.view.View;
import org.kie.workbench.common.stunner.core.graph.content.view.ViewConnector;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvas;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.client.shape.EdgeShape;
import org.kie.workbench.common.stunner.core.client.shape.GraphShape;
import org.kie.workbench.common.stunner.core.client.shape.MutationContext;
import org.kie.workbench.common.stunner.core.client.shape.Shape;
import org.kie.workbench.common.stunner.core.client.shape.factory.ShapeFactory;
import org.kie.workbench.common.stunner.core.client.shape.util.EdgeMagnetsHelper;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class NodeDragProxyImpl implements NodeDragProxy<AbstractCanvasHandler> {
    
    private AbstractCanvasHandler canvasHandler;

    ShapeDragProxy<AbstractCanvas> shapeDragProxyFactory;
    EdgeMagnetsHelper magnetsHelper;

    private EdgeShape transientEdgeShape;
    @Inject
    public NodeDragProxyImpl( final ShapeDragProxy<AbstractCanvas> shapeDragProxyFactory,
                              final EdgeMagnetsHelper magnetsHelper ) {
        this.shapeDragProxyFactory = shapeDragProxyFactory;
        this.magnetsHelper = magnetsHelper;
    }

    @Override
    public DragProxy<AbstractCanvasHandler, Item, NodeDragProxyCallback> proxyFor( final AbstractCanvasHandler context) {
        this.canvasHandler = context;
        this.shapeDragProxyFactory.proxyFor( context.getCanvas() );
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public DragProxy<AbstractCanvasHandler, Item, NodeDragProxyCallback> show( final Item item,
                                                                               final int x,
                                                                               final int y,
                                                                               final NodeDragProxyCallback callback) {

        final AbstractCanvas canvas = canvasHandler.getCanvas();

        final Node<View<?>, Edge> node = item.getNode();

        final ShapeFactory<Object, AbstractCanvasHandler, ?> nodeShapeFactory = item.getNodeShapeFactory();

        final Edge<View<?>, Node> inEdge = item.getInEdge();

        final Node<View<?>, Edge> inEdgeSourceNode = item.getInEdgeSourceNode();
        
        final ShapeFactory<Object, AbstractCanvasHandler, ?> edgeShapeFactory = item.getInEdgeShapeFactory();

        final Shape nodeShape = nodeShapeFactory.build( node.getContent().getDefinition(), canvasHandler );
        
        if ( nodeShape instanceof GraphShape) {

            ( (GraphShape) nodeShape ).applyProperties( node, MutationContext.STATIC );
            
        }
            
        this.transientEdgeShape =
                (EdgeShape) edgeShapeFactory.build( inEdge.getContent().getDefinition(), canvasHandler );
        canvas.addTransientShape( this.transientEdgeShape );
        this.transientEdgeShape.applyProperties( inEdge, MutationContext.STATIC );
        
        final Shape<?> edgeSourceNodeShape = canvasHandler.getCanvas().getShape( inEdgeSourceNode.getUUID() );

        shapeDragProxyFactory.show( nodeShape, x, y, new DragProxyCallback() {
            
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

                final int[] magnets = getMagnets();

                callback.onComplete( x, y );

                callback.onComplete( x, y, magnets[0], magnets[1] );
                
                deleteTransientEdgeShape();

                canvas.draw();

            }
            
            private void drawEdge() {


                if ( inEdge.getContent() instanceof ViewConnector) {

                    final int[] magnets = getMagnets();

                    final ViewConnector viewConnector = (ViewConnector) inEdge.getContent();

                    viewConnector.setSourceMagnetIndex( magnets[0] );
                    viewConnector.setTargetMagnetIndex( magnets[1] );

                }

                NodeDragProxyImpl.this.transientEdgeShape.applyConnections( inEdge,
                        edgeSourceNodeShape.getShapeView(), 
                        nodeShape.getShapeView(), 
                        MutationContext.STATIC );
                
                canvas.draw();
                
            }

            private int[] getMagnets() {

                return magnetsHelper.getDefaultMagnetsIndex( edgeSourceNodeShape.getShapeView(),
                        nodeShape.getShapeView() );

            }
            
        });
        
        return this;
    }

    @Override
    public void clear() {
        if ( null != shapeDragProxyFactory ) {
            this.shapeDragProxyFactory.clear();
        }
        deleteTransientEdgeShape();
    }

    @Override
    public void destroy() {
        if ( null != shapeDragProxyFactory ) {
            clear();
            this.shapeDragProxyFactory.destroy();
        }
        this.shapeDragProxyFactory = null;
        this.canvasHandler = null;
        this.magnetsHelper = null;
        this.transientEdgeShape = null;
        
    }

    private AbstractCanvas getCanvas() {
        return canvasHandler.getCanvas();
    }

    private void deleteTransientEdgeShape() {

        if ( null != this.transientEdgeShape ) {
            getCanvas().deleteTransientShape( this.transientEdgeShape );
            getCanvas().draw();
            this.transientEdgeShape = null;
        }
    }

}
