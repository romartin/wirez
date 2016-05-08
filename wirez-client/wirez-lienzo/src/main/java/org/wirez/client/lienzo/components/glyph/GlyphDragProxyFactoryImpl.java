package org.wirez.client.lienzo.components.glyph;

import org.wirez.client.lienzo.canvas.lienzo.LienzoLayer;
import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.components.drag.DragProxyCallback;
import org.wirez.core.client.components.drag.DragProxyFactory;
import org.wirez.core.client.components.drag.PrimitiveDragProxyFactory;
import org.wirez.core.client.components.glyph.GlyphDragProxyFactory;
import org.wirez.core.client.shape.view.ShapeGlyph;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GlyphDragProxyFactoryImpl implements GlyphDragProxyFactory<AbstractCanvas> {

    PrimitiveDragProxyFactory primitiveDragProxyFactory;

    @Inject
    public GlyphDragProxyFactoryImpl(final PrimitiveDragProxyFactory primitiveDragProxyFactory) {
        this.primitiveDragProxyFactory = primitiveDragProxyFactory;
    }

    @Override
    public DragProxyFactory<AbstractCanvas, ShapeGlyph, DragProxyCallback> proxyFor(final AbstractCanvas context) {
        final LienzoLayer layer = (LienzoLayer) context.getLayer();
        this.primitiveDragProxyFactory.proxyFor( layer.getLienzoLayer() );
        return this;
    }

    @Override
    public DragProxyFactory<AbstractCanvas, ShapeGlyph, DragProxyCallback> newInstance(final ShapeGlyph item,
                                                                               final int x,
                                                                               final int y,
                                                                               final DragProxyCallback callback) {
        
        primitiveDragProxyFactory.newInstance( item.getGroup(), x, y, callback);
        return this;
    }

}
