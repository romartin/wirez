package org.kie.workbench.common.stunner.core.graph.processing.traverse.tree;

import org.kie.workbench.common.stunner.core.graph.processing.traverse.TraverseProcessor;
import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Graph;
import org.kie.workbench.common.stunner.core.graph.Node;

/**
 *  <p>Basic contract for any processor that traverse over all elements in the graph by moving to adjacent nodes/vertices.</p>
 */
public interface TreeTraverseProcessor<G extends Graph, N extends Node, E extends Edge> 
        extends TraverseProcessor<G, TreeTraverseCallback<G, N, E>> {
    
}
