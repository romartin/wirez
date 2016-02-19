package org.wirez.core.api.graph.processing.visitor;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;

public interface ChildrenVisitorCallback<N extends Node, E extends Edge, G extends Graph<?, N>> 
        extends VisitorCallback<N, E, G> {
    
    void visitChildNode(Node parent, Node child);
    
}
