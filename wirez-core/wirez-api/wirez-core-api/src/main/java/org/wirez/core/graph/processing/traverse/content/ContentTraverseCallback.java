package org.wirez.core.graph.processing.traverse.content;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Graph;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;

public interface ContentTraverseCallback<C, N extends Node<View, Edge>, E extends Edge<C, Node>> {

    void startGraphTraversal(Graph<View, N> graph);

    void startEdgeTraversal(E edge);

    void endEdgeTraversal(E edge);

    void startNodeTraversal(N node);
    
    void endNodeTraversal(N node);

    void endGraphTraversal();
    
}
