package org.kie.workbench.common.stunner.core.client.components.drag;

import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.view.View;
import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;
import org.kie.workbench.common.stunner.core.client.shape.factory.ShapeFactory;

public interface NodeDragProxy<H extends CanvasHandler> extends DragProxy<H, NodeDragProxy.Item, NodeDragProxyCallback> {
    
    interface Item<H> {

        Node<View<?>, Edge> getNode();
        
        ShapeFactory<?, H, ?> getNodeShapeFactory();

        Edge<View<?>, Node> getInEdge();

        Node<View<?>, Edge> getInEdgeSourceNode();
        
        ShapeFactory<?, H, ?> getInEdgeShapeFactory();
        
    }

}
