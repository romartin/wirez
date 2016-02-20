package org.wirez.core.api.graph.processing.traverse.tree;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;

/**
 *  <p>Traverses over all elements in the graph by moving to adjacent nodes/vertices, no matter their content.</p>
 */
public interface TreeWalkTraverseProcessor extends TreeTraverseProcessor<Graph, Node, Edge> {

    enum TraversePolicy {
        VISIT_EDGE_BEFORE_TARGET_NODE,
        VISIT_EDGE_AFTER_TARGET_NODE;
    }

    TreeWalkTraverseProcessor usePolicy(TraversePolicy policy);
    
}
