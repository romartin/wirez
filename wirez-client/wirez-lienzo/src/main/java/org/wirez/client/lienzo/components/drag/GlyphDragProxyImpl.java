package org.wirez.client.lienzo.components.drag;

import org.wirez.client.lienzo.LienzoLayer;
import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.components.drag.DragProxyCallback;
import org.wirez.core.client.components.drag.DragProxy;
import org.wirez.core.client.components.drag.PrimitiveDragProxy;
import org.wirez.core.client.components.drag.GlyphDragProxy;
import org.wirez.core.client.shape.view.ShapeGlyph;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GlyphDragProxyImpl implements GlyphDragProxy<AbstractCanvas> {

    PrimitiveDragProxy primitiveDragProxyFactory;

    @Inject
    public GlyphDragProxyImpl( final PrimitiveDragProxy primitiveDragProxyFactory) {
        this.primitiveDragProxyFactory = primitiveDragProxyFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public DragProxy<AbstractCanvas, ShapeGlyph, DragProxyCallback> proxyFor( final AbstractCanvas context) {
        final LienzoLayer layer = (LienzoLayer) context.getLayer();
        this.primitiveDragProxyFactory.proxyFor( layer.getLienzoLayer() );
        
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public DragProxy<AbstractCanvas, ShapeGlyph, DragProxyCallback> show( final ShapeGlyph item,
                                                                          final int x,
                                                                          final int y,
                                                                          final DragProxyCallback callback) {
        
        primitiveDragProxyFactory.show( item.getGroup(), x, y, callback);
        
        return this;
    }

    @Override
    public void clear() {
        if ( null != this.primitiveDragProxyFactory ) {
            this.primitiveDragProxyFactory.clear();
        }
    }

    @Override
    public void destroy() {
        if ( null != this.primitiveDragProxyFactory ) {
            this.primitiveDragProxyFactory.destroy();
        }
        this.primitiveDragProxyFactory = null;
        
    }

}
