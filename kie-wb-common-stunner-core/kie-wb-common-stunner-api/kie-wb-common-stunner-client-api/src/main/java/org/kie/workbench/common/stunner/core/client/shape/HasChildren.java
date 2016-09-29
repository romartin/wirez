package org.kie.workbench.common.stunner.core.client.shape;

public interface HasChildren<T> {

    enum Layout {

        CENTER,
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }
    
    void addChild(T child, Layout layout);
    
    void removeChild(T child);
    
    Iterable<T> getChildren();
    
}
