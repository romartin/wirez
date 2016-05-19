package org.wirez.core.graph.processing.index.bounds;

public interface BoundsIndexer<C, T> {

    BoundsIndexer<C, T> build( C context );
    
    T getAt( double x, double y );
    
    void destroy();
    
}
