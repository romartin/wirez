package org.wirez.core.client.components.drag;

import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.components.drag.DragProxyCallback;
import org.wirez.core.client.components.drag.DragProxy;
import org.wirez.core.client.shape.view.ShapeGlyph;

public interface GlyphDragProxy<C extends Canvas> extends DragProxy<C, ShapeGlyph, DragProxyCallback> {
    
}
