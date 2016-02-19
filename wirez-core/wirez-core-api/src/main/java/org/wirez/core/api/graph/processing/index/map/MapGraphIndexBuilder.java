package org.wirez.core.api.graph.processing.index.map;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.processing.index.GraphIndex;
import org.wirez.core.api.graph.processing.index.GraphIndexBuilder;

import javax.enterprise.context.Dependent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Dependent
public class MapGraphIndexBuilder implements GraphIndexBuilder<Graph<?, Node>, Node, Edge> {
    
    @Override
    public GraphIndex<Node, Edge> build(final Graph<?, Node> graph) {
        assert graph != null;
        
        final Map<String, Node> nodes = new HashMap<>();
        final Map<String, Edge> edges = new HashMap<>();
        
        Iterable<Node> nodesIter = graph.nodes();
        for (Node node : nodesIter) {
            processNode(nodes, edges, node);
        }

        return new MapGraphIndex(nodes, edges);
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
