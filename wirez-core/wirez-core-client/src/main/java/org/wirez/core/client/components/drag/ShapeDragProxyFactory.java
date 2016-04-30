package org.wirez.core.client.components.drag;

import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.shape.Shape;

public interface ShapeDragProxyFactory<C extends Canvas> extends DragProxyFactory<C, Shape<?>, DragProxyCallback> {
    
}
