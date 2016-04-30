package org.wirez.core.client.canvas.wires;

import com.ait.lienzo.client.core.shape.wires.WiresConnector;
import com.ait.lienzo.client.core.shape.wires.WiresContainer;
import com.ait.lienzo.client.core.shape.wires.WiresMagnet;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.shape.view.ShapeView;

public final class WiresUtils {
    
    public static Node getNode(final AbstractCanvasHandler canvasHandler,
                               final WiresContainer shape) {
        
        if ( shape instanceof ShapeView ) {

            final ShapeView view = (ShapeView) shape;

            return canvasHandler.getGraphIndex().getNode(view.getUUID());
            
        }
        
        return null;
    }
    
    public static Node getNode(final AbstractCanvasHandler canvasHandler,
                               final WiresMagnet magnet) {

        final WiresShape shape = magnet.getMagnets().getWiresShape();
        
        if ( shape instanceof ShapeView ) {
            
            final ShapeView view = (ShapeView) shape;

            return canvasHandler.getGraphIndex().getNode(view.getUUID());
        }
        
        return null;
    }

    public static Edge getEdge(final AbstractCanvasHandler canvasHandler,
                               final WiresConnector connector) {

        if ( connector instanceof ShapeView ) {

            final ShapeView view = (ShapeView) connector;

            return canvasHandler.getGraphIndex().getEdge(view.getUUID());
            
        }
        
        return null;
    }
    
}
