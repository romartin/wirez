package org.wirez.core.client.components.drag;

import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.shape.view.ShapeView;

public interface ShapeViewDragProxyFactory<C extends Canvas> extends DragProxyFactory<C, ShapeView<?>, DragProxyCallback> {
    
}
