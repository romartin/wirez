package org.kie.workbench.common.stunner.core.graph.processing.traverse.content;

import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.relationship.Child;
import org.kie.workbench.common.stunner.core.graph.content.view.View;

import java.util.Iterator;

public abstract class AbstractChildrenTraverseCallback<N extends Node<View, Edge>, E extends Edge<Child, Node>>
        extends AbstractContentTraverseCallback<Child, N, E>
        implements ChildrenTraverseCallback<N, E> {

    @Override
    public boolean startNodeTraversal( final Iterator<N> parents,
                                       final N node ) {
        return true;
    }
}
