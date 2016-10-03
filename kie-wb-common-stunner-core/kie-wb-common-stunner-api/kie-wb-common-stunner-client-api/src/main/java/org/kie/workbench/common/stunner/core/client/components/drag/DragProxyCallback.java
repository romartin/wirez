package org.kie.workbench.common.stunner.core.client.components.drag;

public interface DragProxyCallback {

    void onStart(int x, int y);
    
    void onMove(int x, int y);

    void onComplete(int x, int y);

}
