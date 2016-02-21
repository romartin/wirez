package org.wirez.core.api.graph.processing.traverse.content;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.Relationship;
import org.wirez.core.api.graph.content.view.View;

public interface FullContentTraverseCallback<N extends Node<View, Edge>, E extends Edge<Object, Node>> 
    extends ContentTraverseCallback<Object, N, E> {

    void traverseViewEdge(E edge);

    void traverseChildEdge(E edge);
    
    void traverseParentEdge(E edge);
    
}
