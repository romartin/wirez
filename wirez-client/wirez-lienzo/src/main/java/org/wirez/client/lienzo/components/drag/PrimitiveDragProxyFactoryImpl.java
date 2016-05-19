package org.wirez.client.lienzo.components.drag;

import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Layer;
import org.wirez.core.client.components.drag.DragProxyCallback;
import org.wirez.core.client.components.drag.DragProxyFactory;
import org.wirez.core.client.components.drag.PrimitiveDragProxyFactory;
import org.wirez.lienzo.primitive.PrimitiveDragProxy;

import javax.enterprise.context.Dependent;

@Dependent
public class PrimitiveDragProxyFactoryImpl implements PrimitiveDragProxyFactory<Layer, IPrimitive<?>> {
    
    private Layer layer;
    
    protected PrimitiveDragProxyFactoryImpl() {
    }
    
    @Override
    public DragProxyFactory<Layer, IPrimitive<?>, DragProxyCallback> proxyFor(final Layer context) {
        this.layer = context;
        return this;
    }

    @Override
    public DragProxyFactory<Layer, IPrimitive<?>, DragProxyCallback> newInstance(final IPrimitive<?> item,
                                                                                 final int x,
                                                                                 final int y,
                                                                                 DragProxyCallback callback) {

        new PrimitiveDragProxy(layer, item, x, y, 200, new PrimitiveDragProxy.Callback() {
            
            @Override
            public void onStart(final int x, 
                                final int y) {
                callback.onStart( x, y );
            }

            @Override
            public void onMove(final int x, final int y) {
                callback.onMove( x, y );
            }

            @Override
            public void onComplete(final int x, final int y) {
                callback.onComplete( x, y );
            }
            
        });
        
        return this;
    }

    @Override
    public void destroy() {
        this.layer = null;
    }
    
}
