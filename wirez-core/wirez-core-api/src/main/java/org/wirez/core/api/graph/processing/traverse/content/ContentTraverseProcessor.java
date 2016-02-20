package org.wirez.core.api.graph.processing.traverse.content;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.graph.processing.traverse.TraverseProcessor;

/**
 * <p>Basic contract for any processor that traverse over adjacent nodes/vertices 
 * following the edge/relationship provided for a certain content.</p>
 */
public interface ContentTraverseProcessor<C, N extends Node<View, Edge>, E extends Edge<C, Node>> 
        extends TraverseProcessor<Graph<View,N>, ContentTraverseCallback<C, N, E>> {
    
}
