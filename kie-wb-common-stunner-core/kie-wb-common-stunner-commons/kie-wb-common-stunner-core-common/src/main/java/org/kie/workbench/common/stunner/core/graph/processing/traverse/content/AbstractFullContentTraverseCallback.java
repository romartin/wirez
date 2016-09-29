package org.kie.workbench.common.stunner.core.graph.processing.traverse.content;

import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Graph;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.definition.DefinitionSet;
import org.kie.workbench.common.stunner.core.graph.content.view.View;
import org.kie.workbench.common.stunner.core.graph.processing.traverse.content.FullContentTraverseCallback;

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
    public void startGraphTraversal(Graph<DefinitionSet, N> graph) {

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
