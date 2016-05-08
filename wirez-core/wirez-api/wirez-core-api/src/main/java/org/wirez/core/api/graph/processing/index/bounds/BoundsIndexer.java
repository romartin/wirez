package org.wirez.core.api.graph.processing.index.bounds;

public interface BoundsIndexer<C, T> {

    BoundsIndexer<C, T> build( C context );
    
    T getAt( double x, double y );
    
}
