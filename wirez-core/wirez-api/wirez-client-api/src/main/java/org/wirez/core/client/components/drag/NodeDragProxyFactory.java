package org.wirez.core.client.components.drag;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.shape.factory.ShapeFactory;

public interface NodeDragProxyFactory<H extends CanvasHandler> extends DragProxyFactory<H, NodeDragProxyFactory.Item, DragProxyCallback> {
    
    interface Item<H> {

        Node<View<?>, Edge> getNode();
        
        ShapeFactory<?, H, ?> getNodeShapeFactory();

        Edge<View<?>, Node> getInEdge();

        Node<View<?>, Edge> getInEdgeSourceNode();
        
        ShapeFactory<?, H, ?> getInEdgeShapeFactory();
        
    }
    
}
