package org.wirez.core.graph.processing.traverse.tree;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Graph;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.processing.traverse.TraverseProcessor;

/**
 *  <p>Basic contract for any processor that traverse over all elements in the graph by moving to adjacent nodes/vertices.</p>
 */
public interface TreeTraverseProcessor<G extends Graph, N extends Node, E extends Edge> 
        extends TraverseProcessor<G, TreeTraverseCallback<G, N, E>> {
    
}
