package org.wirez.client.lienzo.components.drag;

import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Layer;
import org.wirez.core.client.components.drag.DragProxyCallback;
import org.wirez.core.client.components.drag.DragProxy;
import org.wirez.core.client.components.drag.PrimitiveDragProxy;
import org.wirez.lienzo.primitive.AbstractDragProxy;

import javax.enterprise.context.Dependent;

@Dependent
public class PrimitiveDragProxyImpl implements PrimitiveDragProxy<Layer, IPrimitive<?>> {

    private Layer layer;
    private AbstractDragProxy<?> proxy;

    protected PrimitiveDragProxyImpl() {
    }

    @Override
    public DragProxy<Layer, IPrimitive<?>, DragProxyCallback> proxyFor( final Layer context ) {
        this.layer = context;
        return this;
    }

    @Override
    public DragProxy<Layer, IPrimitive<?>, DragProxyCallback> show( final IPrimitive<?> item,
                                                                    final int x,
                                                                    final int y,
                                                                    DragProxyCallback callback ) {

        this.proxy = new org.wirez.lienzo.primitive.PrimitiveDragProxy( layer, item, x, y, 200, new org.wirez.lienzo.primitive.PrimitiveDragProxy.Callback() {

            @Override
            public void onStart( final int x,
                                 final int y ) {
                callback.onStart( x, y );
            }

            @Override
            public void onMove( final int x, final int y ) {
                callback.onMove( x, y );
            }

            @Override
            public void onComplete( final int x, final int y ) {
                callback.onComplete( x, y );
            }

        } );

        return this;
    }

    @Override
    public void clear() {
        if ( null != this.proxy ) {
            this.proxy.destroy();
            this.layer.draw();
        }
    }

    @Override
    public void destroy() {
        if ( null != this.proxy ) {
            this.proxy.destroy();
            this.proxy = null;
        }
        this.layer = null;
    }

}
