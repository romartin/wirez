package org.wirez.core.api.graph.processing.traverse.tree;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;

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
        traverseGraph();
        endTraverse();
    }

    protected void endTraverse() {
        callback.traverseCompleted();
    }

    protected void traverseGraph() {
        assert graph != null && callback != null;

        doTraverseGraph();

        Collection<Node> startingNodes = getStartingNodes(graph);
        if (!startingNodes.isEmpty()) {
            for (Node node : startingNodes) {
                traverseNode(node);
            }
        }
    }

    protected void doTraverseGraph() {
        callback.traverseGraph(graph);
    }

    protected void traverseNode(final Node graphNode) {
        final String uuid = graphNode.getUUID();
        if ( !this.processesNodes.contains(uuid) ) {
            this.processesNodes.add(uuid);
            if ( doTraverseNode(graphNode) ) {
                List<Edge> outEdges = graphNode.getOutEdges();
                if (outEdges != null && !outEdges.isEmpty()) {
                    for (Edge edge : outEdges) {
                        traverseEdge(edge);
                    }
                }
                
            }
        }
    }

    protected boolean doTraverseNode(final Node node) {
        return callback.traverseNode(node);
    }

    protected void traverseEdge(final Edge edge) {
        final String uuid = edge.getUUID();
        if (!this.processesEdges.contains(uuid)) {
            processesEdges.add(uuid);

            boolean isTraverNode = true;
            if (TraversePolicy.VISIT_EDGE_BEFORE_TARGET_NODE.equals(policy)) {
                isTraverNode = doTraverseEdge(edge);
            }

            if ( isTraverNode ) {
                final Node outNode = edge.getTargetNode();
                if (outNode != null) {
                    traverseNode(outNode);
                }
            }

            if (TraversePolicy.VISIT_EDGE_AFTER_TARGET_NODE.equals(policy)) {
                doTraverseEdge(edge);
            }
        }
    }

    protected boolean doTraverseEdge(final Edge edge) {
        return callback.traverseEdge(edge);
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
