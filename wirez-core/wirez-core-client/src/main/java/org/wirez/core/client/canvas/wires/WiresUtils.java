package org.wirez.core.client.canvas.wires;

import com.ait.lienzo.client.core.shape.wires.WiresConnector;
import com.ait.lienzo.client.core.shape.wires.WiresContainer;
import com.ait.lienzo.client.core.shape.wires.WiresMagnet;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.view.ShapeView;

public final class WiresUtils {
    
    public static Node getNode(final AbstractCanvasHandler canvasHandler,
                               final WiresContainer shape) {
        
        final ShapeView view = (ShapeView) shape;
        
        return canvasHandler.getGraphIndex().getNode(view.getUUID());
    }
    
    public static Node getNode(final AbstractCanvasHandler canvasHandler,
                               final WiresMagnet magnet) {

        final Shape sourceShape = (Shape) magnet.getMagnets().getWiresShape();
        

        return canvasHandler.getGraphIndex().getNode(sourceShape.getUUID());
    }

    public static Edge getEdge(final AbstractCanvasHandler canvasHandler,
                               final WiresConnector connector) {

        final ShapeView view = (ShapeView) connector;

        return canvasHandler.getGraphIndex().getEdge(view.getUUID());
    }
    
}
