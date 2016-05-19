package org.wirez.core.graph.processing.traverse.content;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;

/**
 * <p>Traverses over the graph by moving to adjacent nodes through edges/relationships with view content.</p>
 */
public interface ViewTraverseProcessor
        extends ContentTraverseProcessor<View<?>, Node<View, Edge>, Edge<View<?>, Node>, ContentTraverseCallback<View<?>, Node<View, Edge>, Edge<View<?>, Node>>> {
   
}
