package org.kie.workbench.common.stunner.client.lienzo.canvas.index.bounds;

import org.kie.workbench.common.stunner.client.lienzo.LienzoLayer;
import org.kie.workbench.common.stunner.client.lienzo.util.LienzoLayerUtils;
import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.view.View;
import org.kie.workbench.common.stunner.core.graph.processing.index.bounds.BoundsIndexer;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvas;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.client.canvas.index.bounds.CanvasBoundsIndexer;
import org.kie.workbench.common.stunner.core.client.shape.Shape;

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

    @Override
    public double[] getTrimmedBounds() {
        // TODO
        return new double[0];
    }

    @Override
    public void destroy() {
        this.canvasHandler = null;
    }

}
