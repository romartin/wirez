package org.wirez.client.lienzo.canvas.index.bounds;

import org.wirez.client.lienzo.canvas.lienzo.LienzoLayer;
import org.wirez.client.lienzo.canvas.lienzo.LienzoLayerUtils;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.graph.processing.index.bounds.BoundsIndexer;
import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.index.bounds.CanvasBoundsIndexer;
import org.wirez.core.client.shape.Shape;

import javax.enterprise.context.Dependent;

@Dependent
public class CanvasBoundsIndexerImpl implements CanvasBoundsIndexer<AbstractCanvasHandler> {

    private AbstractCanvasHandler canvasHandler;
    
    public BoundsIndexer<AbstractCanvasHandler, Node<View<?>, Edge>> build(final AbstractCanvasHandler context) {
        this.canvasHandler = context;
        return this;
    }

    @Override
    public Node<View<?>, Edge> getAt(final double x,
                                     final double y) {
    
        final AbstractCanvas canvas = canvasHandler.getCanvas();
        
        final LienzoLayer lienzoLayer = (LienzoLayer) canvas.getLayer();
        
        final String viewUUID = LienzoLayerUtils.getUUID_At( lienzoLayer, x, y );
        
        if ( null != viewUUID && viewUUID.trim().length() > 0 ) {
        
            final Shape<?> shape = canvas.getShape( viewUUID );
        
            if ( null != shape ) {
                
                return canvasHandler.getGraphIndex().getNode( shape.getUUID() );
                
            }
                
        }
        
        return null;
    }
    
}
