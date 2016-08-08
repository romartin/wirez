package org.wirez.core.graph.processing.traverse.content;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Graph;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.definition.DefinitionSet;
import org.wirez.core.graph.content.view.View;

public abstract class AbstractContentTraverseCallback<C, N extends Node<View, Edge>, E extends Edge<C, Node>> implements ContentTraverseCallback<C, N, E> {
    
    @Override
    public void startGraphTraversal(final Graph<DefinitionSet, N> graph) {
        
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
