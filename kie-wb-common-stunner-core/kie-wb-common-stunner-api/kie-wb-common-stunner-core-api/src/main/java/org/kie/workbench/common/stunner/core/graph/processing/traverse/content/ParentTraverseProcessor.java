package org.kie.workbench.common.stunner.core.graph.processing.traverse.content;

import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.relationship.Parent;
import org.kie.workbench.common.stunner.core.graph.content.view.View;

/**
 * <p>Traverses over the graph by moving to adjacent parent nodes.</p>
 */
public interface ParentTraverseProcessor 
        extends ContentTraverseProcessor<Parent, Node<View, Edge>, Edge<Parent, Node>, ContentTraverseCallback<Parent, Node<View, Edge>, Edge<Parent, Node>>> {

}
