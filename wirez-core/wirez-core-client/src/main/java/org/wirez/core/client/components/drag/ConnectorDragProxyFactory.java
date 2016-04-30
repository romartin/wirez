package org.wirez.core.client.components.drag;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.shape.factory.ShapeFactory;

public interface ConnectorDragProxyFactory<H extends CanvasHandler> extends DragProxyFactory<H, ConnectorDragProxyFactory.Item, DragProxyCallback> {
    
    interface Item<H> {

        Edge<View<?>, Node> getEdge();
        
        Node<View<?>, Edge> getSourceNode();
        
        ShapeFactory<?, H, ?> getShapeFactory();
        
    }
    
}
