package org.wirez.core.api.graph.processing.index;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;

public interface IndexBuilder<G extends Graph<?, N>, N extends Node, E extends Edge, I extends Index<N, E>> {

    /**
     * Build the index for the given graph.
     */
    I build(G graph);

    /**
     * Updates a given index. Can be an expensive call.
     * @param index
     */
    void update(I index, G graph);
    
}
