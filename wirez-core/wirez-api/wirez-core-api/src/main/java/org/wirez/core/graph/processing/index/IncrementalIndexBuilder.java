package org.wirez.core.graph.processing.index;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Graph;
import org.wirez.core.graph.Node;

public interface IncrementalIndexBuilder<G extends Graph<?, N>, N extends Node, E extends Edge, I extends Index<N, E>>
 extends IndexBuilder<G, N, E, I> {

    /**
     * Adds a node into the given index.
     */
    IncrementalIndexBuilder<G, N, E, I> addNode(I index, N node);

    /**
     * Removes a node from the given index.
     */
    IncrementalIndexBuilder<G, N, E, I> removeNode(I index, N node);

    /**
     * Adds an edge into the given index.
     */
    IncrementalIndexBuilder<G, N, E, I> addEdge(I index, E edge);

    /**
     * Removes an edge from the given index.
     */
    IncrementalIndexBuilder<G, N, E, I> removeEdge(I index, E edge);

    /**
     * Clears an index.
     */
    IncrementalIndexBuilder<G, N, E, I> clear(I index);
    
}
