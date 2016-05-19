package org.wirez.core.graph.processing.traverse.content;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Graph;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.graph.processing.traverse.TraverseProcessor;

/**
 * <p>Basic contract for any processor that traverse over adjacent nodes/vertices 
 * following the edge/relationship provided for a certain content.</p>
 */
public interface ContentTraverseProcessor<C, N extends Node<View, Edge>, E extends Edge<C, Node>, K extends ContentTraverseCallback<C, N, E>> 
        extends TraverseProcessor<Graph<View,N>, K> {
    
}
