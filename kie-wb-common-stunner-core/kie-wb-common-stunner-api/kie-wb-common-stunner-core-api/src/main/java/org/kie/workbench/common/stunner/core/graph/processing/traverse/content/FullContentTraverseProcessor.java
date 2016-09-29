package org.kie.workbench.common.stunner.core.graph.processing.traverse.content;

import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.content.view.View;

/**
 * <p>Traverses over the graph by moving to adjacent nodes through edges/relationships with both <i>view</i> and <i>child</i> contents.</p>
 */
public interface FullContentTraverseProcessor 
        extends ContentTraverseProcessor<Object, Node<View, Edge>, Edge<Object, Node>, FullContentTraverseCallback<Node<View, Edge>, Edge<Object, Node>>> {
   
}
