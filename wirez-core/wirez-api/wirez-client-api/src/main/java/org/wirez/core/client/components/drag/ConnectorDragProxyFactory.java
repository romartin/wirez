package org.wirez.core.client.components.drag;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.shape.factory.ShapeFactory;

public interface ConnectorDragProxyFactory<H extends CanvasHandler> extends DragProxyFactory<H, ConnectorDragProxyFactory.Item, DragProxyCallback> {
    
    interface Item<H> {

        Edge<View<?>, Node> getEdge();
        
        Node<View<?>, Edge> getSourceNode();
        
        ShapeFactory<?, H, ?> getShapeFactory();
        
    }
    
}
