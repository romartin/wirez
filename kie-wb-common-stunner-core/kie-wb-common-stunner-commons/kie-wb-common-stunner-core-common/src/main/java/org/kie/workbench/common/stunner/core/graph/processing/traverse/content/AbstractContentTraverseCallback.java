package org.kie.workbench.common.stunner.core.graph.processing.traverse.content;

import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Graph;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.definition.DefinitionSet;
import org.kie.workbench.common.stunner.core.graph.content.view.View;
import org.kie.workbench.common.stunner.core.graph.processing.traverse.content.ContentTraverseCallback;

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
