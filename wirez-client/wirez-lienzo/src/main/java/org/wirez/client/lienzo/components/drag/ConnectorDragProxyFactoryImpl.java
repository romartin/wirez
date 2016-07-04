package org.wirez.client.lienzo.components.drag;

import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.wires.WiresConnector;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import org.wirez.client.lienzo.LienzoLayer;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.graph.processing.index.bounds.GraphBoundsIndexer;
import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.components.drag.ConnectorDragProxyFactory;
import org.wirez.core.client.components.drag.DragProxyCallback;
import org.wirez.core.client.components.drag.DragProxyFactory;
import org.wirez.core.client.components.drag.ShapeViewDragProxyFactory;
import org.wirez.core.client.shape.EdgeShape;
import org.wirez.core.client.shape.MutationContext;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.factory.ShapeFactory;
import org.wirez.core.client.shape.view.ShapeView;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class ConnectorDragProxyFactoryImpl implements ConnectorDragProxyFactory<AbstractCanvasHandler> {

    private AbstractCanvasHandler canvasHandler;

    ShapeViewDragProxyFactory<AbstractCanvas> shapeViewDragProxyFactory;
    GraphBoundsIndexer graphBoundsIndexer;

    @Inject
    public ConnectorDragProxyFactoryImpl(final ShapeViewDragProxyFactory<AbstractCanvas> shapeViewDragProxyFactory,
                                         final GraphBoundsIndexer graphBoundsIndexer) {
        this.shapeViewDragProxyFactory = shapeViewDragProxyFactory;
        this.graphBoundsIndexer = graphBoundsIndexer;
    }

    @Override
    public DragProxyFactory<AbstractCanvasHandler, Item, DragProxyCallback> proxyFor(final AbstractCanvasHandler context) {
        this.canvasHandler = context;
        this.shapeViewDragProxyFactory.proxyFor( context.getCanvas() );
        this.graphBoundsIndexer.setRootUUID( context.getDiagram().getSettings().getCanvasRootUUID() );
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public DragProxyFactory<AbstractCanvasHandler, Item, DragProxyCallback> newInstance(Item item, int x, int y, DragProxyCallback callback) {
        
        final Edge<View<?>, Node> edge = item.getEdge();
        final Node<View<?>, Edge> sourceNode = item.getSourceNode();
        
        final ShapeFactory<Object, AbstractCanvasHandler, ?> factory =
                (ShapeFactory<Object, AbstractCanvasHandler, ?>) item.getShapeFactory();

        final AbstractCanvas<?> canvas = canvasHandler.getCanvas();
        final LienzoLayer layer = (LienzoLayer) canvas.getLayer();
        final WiresManager wiresManager = WiresManager.get( layer.getLienzoLayer() );
        
        final Shape<?> sourceNodeShape = canvas.getShape( sourceNode.getUUID() );
        final Shape<?> shape = factory.build( edge.getContent().getDefinition(), canvasHandler );
        
        final EdgeShape connector = (EdgeShape) shape;
        final WiresConnector wiresConnector = (WiresConnector) shape.getShapeView();
        wiresManager.registerConnector( wiresConnector );

        final MultiPath dummyPath = new MultiPath().rect(0, 0, 1, 1).setFillAlpha(0).setStrokeAlpha(0);
        final DummyShapeView dummyShapeView = new DummyShapeView( dummyPath, wiresManager );
        
        graphBoundsIndexer.build( canvasHandler.getDiagram().getGraph() );

        shapeViewDragProxyFactory.newInstance( dummyShapeView, x, y, new DragProxyCallback() {
            
            @Override
            public void onStart(final int x, 
                                final int y) {
                
                callback.onStart( x ,y );
                
                drawConnector();
                
            }

            @Override
            public void onMove(final int x, 
                               final int y) {

                callback.onMove( x , y );

                drawConnector();
                
            }

            @Override
            public void onComplete(final int x, 
                                   final int y) {
                
                callback.onComplete( x ,y );

                wiresManager.deregisterConnector( wiresConnector );

                canvas.draw();
                
            }
            
            private void drawConnector() {
                

                ShapeView<?> targetShapeView = null;

                // TODO: Apply target connection to mouse pointer, in adittion check if allowed connection to node at current pos is ok -> automatically connect to it
                // final Node targetNode = graphBoundsIndexer.getAt( x, y );
                final Node targetNode = null;
                if ( null != targetNode ) {
                    
                    final Shape<?> targetNodeShape = canvas.getShape( targetNode.getUUID() );
                    if ( null != targetNodeShape ) {
                        targetShapeView = targetNodeShape.getShapeView();
                    }
                    
                } else {
                    
                    targetShapeView = dummyShapeView;
                }

                connector.applyConnections( edge, sourceNodeShape.getShapeView(), targetShapeView, MutationContext.STATIC );
                connector.applyProperties( edge, MutationContext.STATIC );
                
                canvas.draw();
            }
            
        });


        return this;
    }

    public void destroy() {

        ConnectorDragProxyFactoryImpl.this.graphBoundsIndexer.destroy();
        ConnectorDragProxyFactoryImpl.this.graphBoundsIndexer = null;
        ConnectorDragProxyFactoryImpl.this.canvasHandler = null;
        ConnectorDragProxyFactoryImpl.this.shapeViewDragProxyFactory.destroy();
        ConnectorDragProxyFactoryImpl.this.shapeViewDragProxyFactory = null;
        
    }
    
    private class DummyShapeView extends WiresShape implements ShapeView<DummyShapeView> {

        public DummyShapeView(MultiPath path, WiresManager manager) {
            super(path, new WiresLayoutContainer(), manager);
        }

        @Override
        public DummyShapeView setUUID(String uuid) {
            return null;
        }

        @Override
        public String getUUID() {
            return null;
        }

        @Override
        public double getShapeX() {
            return 0;
        }

        @Override
        public double getShapeY() {
            return 0;
        }

        @Override
        public DummyShapeView setShapeX(double x) {
            return null;
        }

        @Override
        public DummyShapeView setShapeY(double y) {
            return null;
        }

        @Override
        public String getFillColor() {
            return null;
        }

        @Override
        public DummyShapeView setFillColor(String color) {
            return null;
        }

        @Override
        public double getFillAlpha() {
            return 0;
        }

        @Override
        public DummyShapeView setFillAlpha(double alpha) {
            return null;
        }

        @Override
        public String getStrokeColor() {
            return null;
        }

        @Override
        public DummyShapeView setStrokeColor(String color) {
            return null;
        }

        @Override
        public double getStrokeAlpha() {
            return 0;
        }

        @Override
        public DummyShapeView setStrokeAlpha(double alpha) {
            return null;
        }

        @Override
        public double getStrokeWidth() {
            return 0;
        }

        @Override
        public DummyShapeView setStrokeWidth(double width) {
            return null;
        }

        @Override
        public DummyShapeView setDragEnabled(boolean isDraggable) {
            return null;
        }

        @Override
        public DummyShapeView moveToTop() {
            return null;
        }

        @Override
        public DummyShapeView moveToBottom() {
            return null;
        }

        @Override
        public DummyShapeView moveUp() {
            return null;
        }

        @Override
        public DummyShapeView moveDown() {
            return null;
        }

        @Override
        public DummyShapeView setZIndex(int zindez) {
            return null;
        }

        @Override
        public int getZIndex() {
            return 0;
        }

        @Override
        public void destroy() {
            
        }
    }
}
