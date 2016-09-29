package org.kie.workbench.common.stunner.core.client.components.drag;

import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.view.View;
import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;
import org.kie.workbench.common.stunner.core.client.shape.factory.ShapeFactory;

public interface ConnectorDragProxy<H extends CanvasHandler> extends DragProxy<H, ConnectorDragProxy.Item, DragProxyCallback> {
    
    interface Item<H> {

        Edge<View<?>, Node> getEdge();
        
        Node<View<?>, Edge> getSourceNode();
        
        ShapeFactory<?, H, ?> getShapeFactory();
        
    }
    
}
