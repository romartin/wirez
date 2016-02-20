package org.wirez.core.api.graph.processing.traverse.tree;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;

public interface TreeTraverseCallback<G extends Graph, N extends Node, E extends Edge> {

    void traverseGraph(G graph);

    boolean traverseNode(N node);

    boolean traverseEdge(E edge);

    void traverseCompleted();
    
}
