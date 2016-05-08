package org.wirez.core.api.graph.processing.traverse.content;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;

public abstract class AbstractContentTraverseCallback<C, N extends Node<View, Edge>, E extends Edge<C, Node>> implements ContentTraverseCallback<C, N, E> {
    
    @Override
    public void startGraphTraversal(final Graph<View, N> graph) {
        
    }

    @Override
    public void startEdgeTraversal(final E edge) {

    }

    @Override
    public void endEdgeTraversal(final E edge) {

    }

    @Override
    public void startNodeTraversal(final N node) {

    }

    @Override
    public void endNodeTraversal(final N node) {

    }

    @Override
    public void endGraphTraversal() {

    }
}
