package org.wirez.core.graph.processing.traverse.content;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Graph;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.relationship.Child;
import org.wirez.core.graph.content.view.View;

import java.util.Iterator;

public interface ChildrenTraverseCallback<N extends Node<View, Edge>, E extends Edge<Child, Node>> extends ContentTraverseCallback<Child, N, E> {

    boolean startNodeTraversal( Iterator<N> parents, N node );
    
}
