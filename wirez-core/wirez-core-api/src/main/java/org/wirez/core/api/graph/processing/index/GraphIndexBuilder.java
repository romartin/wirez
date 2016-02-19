package org.wirez.core.api.graph.processing.index;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;

public interface GraphIndexBuilder<G extends Graph<?, N>, N extends Node, E extends Edge> {

    /**
     * Build the index for the given graph.
     */
    GraphIndex<N, E> build(G graph);

    
    
}
