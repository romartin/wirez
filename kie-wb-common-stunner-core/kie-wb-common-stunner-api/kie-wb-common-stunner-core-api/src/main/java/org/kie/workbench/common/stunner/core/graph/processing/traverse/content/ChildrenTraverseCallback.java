package org.kie.workbench.common.stunner.core.graph.processing.traverse.content;

import org.kie.workbench.common.stunner.core.graph.content.relationship.Child;
import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.view.View;

import java.util.Iterator;

public interface ChildrenTraverseCallback<N extends Node<View, Edge>, E extends Edge<Child, Node>> extends ContentTraverseCallback<Child, N, E> {

    boolean startNodeTraversal( Iterator<N> parents, N node );
    
}
