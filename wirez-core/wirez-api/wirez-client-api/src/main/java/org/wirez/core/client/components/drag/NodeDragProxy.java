package org.wirez.core.client.components.drag;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.shape.factory.ShapeFactory;

public interface NodeDragProxy<H extends CanvasHandler> extends DragProxy<H, NodeDragProxy.Item, NodeDragProxyCallback> {
    
    interface Item<H> {

        Node<View<?>, Edge> getNode();
        
        ShapeFactory<?, H, ?> getNodeShapeFactory();

        Edge<View<?>, Node> getInEdge();

        Node<View<?>, Edge> getInEdgeSourceNode();
        
        ShapeFactory<?, H, ?> getInEdgeShapeFactory();
        
    }

}
