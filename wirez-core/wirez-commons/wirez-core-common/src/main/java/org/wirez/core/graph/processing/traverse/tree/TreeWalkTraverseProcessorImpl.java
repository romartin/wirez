package org.wirez.core.graph.processing.traverse.tree;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Graph;
import org.wirez.core.graph.Node;

import javax.enterprise.context.Dependent;
import java.util.*;

@Dependent
public final class TreeWalkTraverseProcessorImpl implements TreeWalkTraverseProcessor {

    private Graph graph;
    private TraversePolicy policy;
    private TreeTraverseCallback<Graph, Node, Edge> callback;
    private final Set<String> processesEdges = new HashSet<String>();
    private final Set<String> processesNodes = new HashSet<String>();


    public TreeWalkTraverseProcessorImpl() {
        this.policy = TraversePolicy.VISIT_EDGE_BEFORE_TARGET_NODE;
    }

    @Override
    public TreeWalkTraverseProcessor usePolicy(final TraversePolicy policy) {
        this.policy = policy;
        return this;
    }
    
    @Override
    public void traverse(final Graph graph, 
                         final TreeTraverseCallback<Graph, Node, Edge> callback) {
        this.graph = graph;
        this.callback = callback;
        
        processesNodes.clear();
        processesEdges.clear();
        
        startTraverse();
    }

    protected void startTraverse() {
        startGraphTraversal();
        endGraphTraversal();
    }

    protected void endGraphTraversal() {
        callback.endGraphTraversal();
        
        this.graph = null;
        this.callback = null;
        this.processesEdges.clear();
        this.processesNodes.clear();
    }

    protected void startGraphTraversal() {
        assert graph != null && callback != null;

        doStartGraphTraversal();

        Collection<Node> startingNodes = getStartingNodes(graph);
        if (!startingNodes.isEmpty()) {
            for (Node node : startingNodes) {
                startNodeTraversal(node);
            }
        }
    }

    protected void doStartGraphTraversal() {
        callback.startGraphTraversal(graph);
    }

    protected void startNodeTraversal(final Node graphNode) {
        final String uuid = graphNode.getUUID();
        if ( !this.processesNodes.contains(uuid) ) {
            this.processesNodes.add(uuid);
            if ( doStartNodeTraversal(graphNode) ) {
                List<Edge> outEdges = graphNode.getOutEdges();
                if (outEdges != null && !outEdges.isEmpty()) {
                    for (Edge edge : outEdges) {
                        startEdgeTraversal(edge);
                    }
                }
            }
            doEndNodeTraversal(graphNode);
        }
    }

    protected boolean doStartNodeTraversal(final Node node) {
        return callback.startNodeTraversal(node);
    }

    protected void doEndNodeTraversal(final Node node) {
        callback.endNodeTraversal(node);
    }

    protected void startEdgeTraversal(final Edge edge) {
        final String uuid = edge.getUUID();
        if (!this.processesEdges.contains(uuid)) {
            processesEdges.add(uuid);

            boolean isTraverNode = true;
            if (TraversePolicy.VISIT_EDGE_BEFORE_TARGET_NODE.equals(policy)) {
                isTraverNode = doStartEdgeTraversal(edge);
            }

            if ( isTraverNode ) {
                final Node outNode = edge.getTargetNode();
                if (outNode != null) {
                    startNodeTraversal(outNode);
                }
            }

            if (TraversePolicy.VISIT_EDGE_AFTER_TARGET_NODE.equals(policy)) {
                doStartEdgeTraversal(edge);
            }
            
            doEndEdgeTraversal(edge);
        }
    }

    protected boolean doStartEdgeTraversal(final Edge edge) {
        return callback.startEdgeTraversal(edge);
    }
    
    protected void doEndEdgeTraversal(final Edge edge) {
        callback.endEdgeTraversal(edge);
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
