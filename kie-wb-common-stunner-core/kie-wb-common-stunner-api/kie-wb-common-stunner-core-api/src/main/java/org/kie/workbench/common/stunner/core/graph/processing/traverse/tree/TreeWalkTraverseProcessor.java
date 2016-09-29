package org.kie.workbench.common.stunner.core.graph.processing.traverse.tree;

import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Graph;
import org.kie.workbench.common.stunner.core.graph.Node;

/**
 *  <p>Traverses over all elements in the graph by moving to adjacent nodes/vertices, no matter their content.</p>
 */
public interface TreeWalkTraverseProcessor extends TreeTraverseProcessor<Graph, Node, Edge> {

    enum EdgeVisitorPolicy {
        VISIT_EDGE_BEFORE_TARGET_NODE,
        VISIT_EDGE_AFTER_TARGET_NODE;
    }

    enum StartingNodesPolicy {
        NO_INCOMING_EDGES,
        NO_INCOMING_VIEW_EDGES
    }

    TreeWalkTraverseProcessor useEdgeVisitorPolicy( EdgeVisitorPolicy policy );

    TreeWalkTraverseProcessor useStartingNodesPolicy( StartingNodesPolicy policy );
    
}
