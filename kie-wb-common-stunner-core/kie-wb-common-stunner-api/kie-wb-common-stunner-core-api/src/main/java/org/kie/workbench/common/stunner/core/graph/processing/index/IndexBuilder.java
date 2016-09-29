package org.kie.workbench.common.stunner.core.graph.processing.index;

import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Graph;
import org.kie.workbench.common.stunner.core.graph.Node;

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
