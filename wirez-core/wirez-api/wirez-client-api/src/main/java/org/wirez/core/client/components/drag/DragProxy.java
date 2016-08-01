package org.wirez.core.client.components.drag;

public interface DragProxy<C, I, K> {

    DragProxy<C, I, K> proxyFor( C context );

    DragProxy<C, I, K> show( I item, int x, int y, K callback );

    void clear();

    void destroy();
    
}
