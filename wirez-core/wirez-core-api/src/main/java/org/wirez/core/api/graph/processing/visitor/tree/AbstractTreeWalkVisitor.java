package org.wirez.core.api.graph.processing.visitor.tree;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.processing.visitor.Visitor;
import org.wirez.core.api.graph.processing.visitor.VisitorCallback;
import org.wirez.core.api.graph.processing.visitor.VisitorPolicy;

import javax.inject.Inject;
import java.util.*;

/**
 * Visits the graph by walking on the different tree nodes and their children in a recursive way. 
 */
public abstract class AbstractTreeWalkVisitor<C extends VisitorCallback<Node, Edge, Graph<?, Node>>> implements Visitor<Graph<?, Node>, C, VisitorPolicy> {


    protected Graph graph;
    protected C callback;
    protected VisitorPolicy policy;
    protected final Set<String> processesEdges = new HashSet<String>();
    protected final Set<String> processesNodes = new HashSet<String>();

    @Override
    public void visit(final Graph<?, Node> graph, 
                      final C callback, 
                      final VisitorPolicy policy) {
        this.graph = graph;
        this.callback = callback;
        this.policy = policy;
        this.processesEdges.clear();
        this.processesNodes.clear();
        
        startVisit();
        
    }
    
    protected void startVisit() {
        visitGraph();
        endVisit();
    }
    
    protected void endVisit() {
        callback.endVisit();
    }

    protected void visitGraph() {
        assert graph != null && callback != null;

        doVisitGraph();
        
        Collection<Node> startingNodes = getStartingNodes(graph);
        if (!startingNodes.isEmpty()) {
            for (Node node : startingNodes) {
                visitNode(node);
            }
        }
        
    }
    
    protected void doVisitGraph() {
        callback.visitGraph(graph);
        ;
    }

    protected void visitNode(final Node graphNode) {

        final String uuid = graphNode.getUUID();
        if ( !this.processesNodes.contains(uuid) ) {
            this.processesNodes.add(uuid);
            
            doVisitNode(graphNode);

            List<Edge> outEdges = graphNode.getOutEdges();
            if (outEdges != null && !outEdges.isEmpty()) {
                for (Edge edge : outEdges) {
                    visitEdge(edge);
                }
            }
            
        }

    }

    protected void doVisitNode(final Node node) {

        callback.visitNode(node);

    }

    protected void visitEdge(final Edge edge) {

        final String uuid = edge.getUUID();
        if (!this.processesEdges.contains(uuid)) {
            processesEdges.add(uuid);
            
            if (VisitorPolicy.VISIT_EDGE_BEFORE_TARGET_NODE.equals(policy)) {
                doVisitEdge(edge);
            }
            
            final Node outNode = edge.getTargetNode();
            if (outNode != null) {
                visitNode(outNode);
            }
            
            if (VisitorPolicy.VISIT_EDGE_AFTER_TARGET_NODE.equals(policy)) {
                doVisitEdge(edge);
            }
        }

    }

    protected void doVisitEdge(final Edge edge) {

        callback.visitEdge(edge);

    }

    protected Collection<Node> getStartingNodes(final Graph graph) {
        final Collection<Node> result = new LinkedList<Node>();

        final Iterator<Node> nodesIt = graph.nodes().iterator();
        while (nodesIt.hasNext()) {
            final Node node = nodesIt.next();
            if (node.getInEdges() == null || !node.getInEdges().iterator().hasNext()) {
                result.add(node);
            }
        }

        return result;
    }
    
    
}
