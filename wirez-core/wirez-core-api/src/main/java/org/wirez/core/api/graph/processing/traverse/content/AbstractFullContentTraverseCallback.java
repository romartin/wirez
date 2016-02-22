package org.wirez.core.api.graph.processing.traverse.content;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;

public abstract class AbstractFullContentTraverseCallback<N extends Node<View, Edge>, E extends Edge<Object, Node>> 
        implements FullContentTraverseCallback<N, E> {
    
    @Override
    public void startViewEdgeTraversal(E edge) {
        
    }

    @Override
    public void endViewEdgeTraversal(E edge) {

    }

    @Override
    public void startChildEdgeTraversal(E edge) {

    }

    @Override
    public void endChildEdgeTraversal(E edge) {

    }

    @Override
    public void startParentEdgeTraversal(E edge) {

    }

    @Override
    public void endParentEdgeTraversal(E edge) {

    }

    @Override
    public void startGraphTraversal(Graph<View, N> graph) {

    }

    @Override
    public void startEdgeTraversal(E edge) {

    }

    @Override
    public void endEdgeTraversal(E edge) {

    }

    @Override
    public void startNodeTraversal(N node) {

    }

    @Override
    public void endNodeTraversal(N node) {

    }

    @Override
    public void endGraphTraversal() {

    }
    
}
