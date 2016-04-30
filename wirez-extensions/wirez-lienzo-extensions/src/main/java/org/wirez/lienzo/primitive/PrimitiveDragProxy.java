package org.wirez.lienzo.primitive;

import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Layer;

public class PrimitiveDragProxy extends AbstractDragProxy<IPrimitive<?>> {

    public PrimitiveDragProxy(final Layer layer,
                              final IPrimitive<?> shape,
                              final int x,
                              final int y,
                              final int timeout,
                              final Callback callback) {

        super(layer, shape, x, y, timeout, callback);

    }
    
    @Override
    protected void addToLayer(final Layer layer, final IPrimitive<?> shape) {
        layer.add( shape );
    }

    @Override
    protected void removeFromLayer(final Layer layer, final IPrimitive<?> shape) {
        shape.removeFromParent();
    }

    @Override
    protected void setX(final IPrimitive<?> shape, final int x) {
        shape.setX( x );
    }

    @Override
    protected void setY(final IPrimitive<?> shape, final int y) {
        shape.setY( y );
    }
    
}