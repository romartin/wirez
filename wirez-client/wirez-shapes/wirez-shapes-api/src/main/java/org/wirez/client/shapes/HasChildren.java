package org.wirez.client.shapes;

public interface HasChildren<T> {

    enum Layout {

        CENTER,
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }
    
    void addChild( T child, Layout layout );
    
    void removeChild( T child );
    
}
