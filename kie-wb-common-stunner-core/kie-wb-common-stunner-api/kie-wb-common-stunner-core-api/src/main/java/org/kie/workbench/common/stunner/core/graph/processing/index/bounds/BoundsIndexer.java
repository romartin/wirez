package org.kie.workbench.common.stunner.core.graph.processing.index.bounds;

public interface BoundsIndexer<C, T> {

    /**
     * Builds a index of all the visible graph elements bounds for a given context ( usually a canvas or canvas handler ).
     */
    BoundsIndexer<C, T> build( C context );

    /**
     * Return the graph element at the given x,y cartesian coordinate.
     */
    T getAt( double x, double y );

    /**
     * Determines a rectangle area which area is given as:
     * - the top left position of the graph element found nearer to this position.
     * - the bottom right position of the graph element found nearer to this position.
     */
    double[] getTrimmedBounds();

    /**
     * Destroy this index.
     */
    void destroy();
    
}
