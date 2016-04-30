package org.wirez.core.client.components.drag;

public interface DragProxyFactory<C, I, K> {

    DragProxyFactory<C, I, K> proxyFor( C context );

    DragProxyFactory<C, I, K> newInstance( I item, int x, int y, K callback );
    
}
