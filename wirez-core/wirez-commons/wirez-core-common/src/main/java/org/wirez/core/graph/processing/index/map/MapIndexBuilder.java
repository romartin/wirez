package org.wirez.core.graph.processing.index.map;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Graph;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.processing.index.IndexBuilder;

import javax.enterprise.context.Dependent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Dependent
public class MapIndexBuilder implements IndexBuilder<Graph<?, Node>, Node, Edge, MapIndex> {
    
    @Override
    public MapIndex build(final Graph<?, Node> graph) {
        assert graph != null;
        
        return doWork(graph, null);
    }

    @Override
    public void update(final MapIndex index, 
                       final Graph<?, Node> graph) {
        
        doWork(graph, index);
        
    }
    
    private MapIndex doWork(final Graph<?, Node> graph,
                            final MapIndex current) {
        
        final Map<String, Node> nodes = new HashMap<>();
        final Map<String, Edge> edges = new HashMap<>();

        Iterable<Node> nodesIter = graph.nodes();
        for (Node node : nodesIter) {
            processNode(nodes, edges, node);
        }

        
        if ( null == current ) {
            // Requesting a new index.
            return new MapIndex(nodes, edges);
        } else {
            // Updating an existing index.
            current.nodes.clear();
            current.nodes.putAll(nodes);
            current.edges.clear();
            current.edges.putAll(edges);
            return current;
        }
        
    }

    private void processNode(final Map<String, Node> nodes, 
                             final Map<String, Edge> edges,
                             final Node node) {
        
        if ( !nodes.containsKey(node.getUUID()) ) {
            nodes.put(node.getUUID(), node);
            
            final List<Edge> outEdges = node.getOutEdges();
            if ( null != outEdges && !outEdges.isEmpty() ) {
                for ( final Edge edge : outEdges ) {
                    processEdge(nodes, edges, edge);
                }
            }
        }
        
    }

    private void processEdge(final Map<String, Node> nodes,
                             final Map<String, Edge> edges,
                             final Edge edge) {
        
        if ( !edges.containsKey(edge.getUUID()) ) {
            edges.put(edge.getUUID(), edge);
        }
        
    }
    
}
