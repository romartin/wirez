package org.kie.workbench.common.stunner.core.graph.processing.traverse.content;

import org.kie.workbench.common.stunner.core.graph.content.relationship.Child;
import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.view.View;

/**
 * <p>Traverses over the graph by moving to adjacent child nodes.</p>
 */
public interface ChildrenTraverseProcessor 
        extends ContentTraverseProcessor<Child, Node<View, Edge>, Edge<Child, Node>, ChildrenTraverseCallback<Node<View, Edge>, Edge<Child, Node>>> {

    ChildrenTraverseProcessor setRootUUID( String rootUUID );

}
