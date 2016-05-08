package org.wirez.core.api.graph.processing.traverse.tree;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;

public interface TreeTraverseCallback<G extends Graph, N extends Node, E extends Edge> {

    void startGraphTraversal(G graph);

    boolean startNodeTraversal(N node);

    boolean startEdgeTraversal(E edge);

    void endNodeTraversal(N node);

    void endEdgeTraversal(E edge);
    
    void endGraphTraversal();
    
    
    
}
