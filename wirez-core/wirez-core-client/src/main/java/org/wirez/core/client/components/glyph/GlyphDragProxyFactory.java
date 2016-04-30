package org.wirez.core.client.components.glyph;

import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.components.drag.DragProxyCallback;
import org.wirez.core.client.components.drag.DragProxyFactory;
import org.wirez.core.client.shape.view.ShapeGlyph;

public interface GlyphDragProxyFactory<C extends Canvas> extends DragProxyFactory<C, ShapeGlyph, DragProxyCallback> {
    
}
