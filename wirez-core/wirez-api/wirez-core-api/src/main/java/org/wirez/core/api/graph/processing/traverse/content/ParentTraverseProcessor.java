package org.wirez.core.api.graph.processing.traverse.content;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.relationship.Parent;
import org.wirez.core.api.graph.content.view.View;

/**
 * <p>Traverses over the graph by moving to adjacent parent nodes.</p>
 */
public interface ParentTraverseProcessor 
        extends ContentTraverseProcessor<Parent, Node<View, Edge>, Edge<Parent, Node>, ContentTraverseCallback<Parent, Node<View, Edge>, Edge<Parent, Node>>> {

}
