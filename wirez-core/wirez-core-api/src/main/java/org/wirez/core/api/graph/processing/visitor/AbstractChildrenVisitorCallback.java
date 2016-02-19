package org.wirez.core.api.graph.processing.visitor;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;

import javax.enterprise.context.Dependent;

public abstract class AbstractChildrenVisitorCallback extends AbstractVisitorCallback 
        implements ChildrenVisitorCallback<Node, Edge, Graph<?, Node>> {
    
    @Override
    public void visitChildNode(Node parent, Node child) {
        
    }
    
}
