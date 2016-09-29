package org.kie.workbench.common.stunner.core.graph.processing.traverse.content;

import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Graph;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.definition.DefinitionSet;
import org.kie.workbench.common.stunner.core.graph.content.view.View;

public interface ContentTraverseCallback<C, N extends Node<View, Edge>, E extends Edge<C, Node>> {

    void startGraphTraversal(Graph<DefinitionSet, N> graph);

    void startEdgeTraversal(E edge);

    void endEdgeTraversal(E edge);

    void startNodeTraversal(N node);
    
    void endNodeTraversal(N node);

    void endGraphTraversal();
    
}
