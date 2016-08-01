package org.wirez.core.client.components.drag;

import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.shape.Shape;

public interface ShapeDragProxy<C extends Canvas> extends DragProxy<C, Shape<?>, DragProxyCallback> {
    
}
