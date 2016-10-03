package org.kie.workbench.common.stunner.core.graph.processing.traverse.content;

import org.kie.workbench.common.stunner.core.graph.processing.traverse.TraverseProcessor;
import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Graph;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.view.View;

/**
 * <p>Basic contract for any processor that traverse over adjacent nodes/vertices 
 * following the edge/relationship provided for a certain content.</p>
 */
public interface ContentTraverseProcessor<C, N extends Node<View, Edge>, E extends Edge<C, Node>, K extends ContentTraverseCallback<C, N, E>> 
        extends TraverseProcessor<Graph<View,N>, K> {
    
}
