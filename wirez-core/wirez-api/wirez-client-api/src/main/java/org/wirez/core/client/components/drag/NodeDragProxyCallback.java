package org.wirez.core.client.components.drag;

public interface NodeDragProxyCallback extends DragProxyCallback {

    void onComplete( int x, int y, int sourceMagnet, int targetMagnet );

}
