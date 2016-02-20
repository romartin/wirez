package org.wirez.core.api.graph.processing.traverse.content;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;

/**
 * <p>Traverses over the graph by moving to adjacent nodes through edges/relationships with view content.</p>
 */
public interface ViewTraverseProcessor extends ContentTraverseProcessor<View<?>, Node<View, Edge>, Edge<View<?>, Node>> {
   
}
