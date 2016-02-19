package org.wirez.core.api.graph.processing.visitor.tree;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ParentChildRelationship;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.processing.visitor.ChildrenVisitorCallback;
import org.wirez.core.api.graph.processing.visitor.VisitorCallback;
import org.wirez.core.api.graph.processing.visitor.VisitorPolicy;

import javax.enterprise.context.Dependent;
import java.util.Stack;

/**
 * Visits the graph by walking on the different tree nodes and their children in a recursive way.
 * If the method <code>visitChildNode</code> is called for a child node, the <code>visitNode</code> method call will be not done for this child.
 * Must be run using the <code>VisitorPolicy.VISIT_EDGE_BEFORE_TARGET_NODE</code> visitor policy.
 */
@Dependent
public class TreeWalkChildrenVisitor extends AbstractTreeWalkVisitor<ChildrenVisitorCallback<Node, Edge, Graph<?, Node>>> {


    private final Stack<Node> parents = new Stack<>();

    @Override
    public void visit(Graph<?, Node> graph, ChildrenVisitorCallback<Node, Edge, Graph<?, Node>> callback, VisitorPolicy policy) {
        assert VisitorPolicy.VISIT_EDGE_BEFORE_TARGET_NODE.equals(policy);
        super.visit(graph, callback, policy);
    }

    @Override
    protected void startVisit() {
        
        super.startVisit();
        parents.clear();
    }

    @Override
    protected void doVisitNode(final Node graphNode) {
        final Node parent = parents.empty() ? null : parents.pop();
        
        if ( null != parent ) {
            callback.visitChildNode(parent, graphNode);
        } else {
            super.doVisitNode(graphNode);
        }
        
    }

    @Override
    protected void doVisitEdge(final Edge edge) {
        final Object content = edge.getContent();
        if (content instanceof ParentChildRelationship) {
            final Node parent = edge.getSourceNode();
            if (null != parent) {
                parents.push(parent);
            }
        }

        super.doVisitEdge(edge);

    }
    
}
