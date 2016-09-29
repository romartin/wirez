package org.kie.workbench.common.stunner.core.client.components.drag;

import org.kie.workbench.common.stunner.core.client.canvas.Canvas;
import org.kie.workbench.common.stunner.core.client.shape.Shape;

public interface ShapeDragProxy<C extends Canvas> extends DragProxy<C, Shape<?>, DragProxyCallback> {
    
}
