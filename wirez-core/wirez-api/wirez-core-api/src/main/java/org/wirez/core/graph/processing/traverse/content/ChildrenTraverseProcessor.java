package org.wirez.core.graph.processing.traverse.content;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.relationship.Child;
import org.wirez.core.graph.content.view.View;

/**
 * <p>Traverses over the graph by moving to adjacent child nodes.</p>
 */
public interface ChildrenTraverseProcessor 
        extends ContentTraverseProcessor<Child, Node<View, Edge>, Edge<Child, Node>, ContentTraverseCallback<Child, Node<View, Edge>, Edge<Child, Node>>> {

}
