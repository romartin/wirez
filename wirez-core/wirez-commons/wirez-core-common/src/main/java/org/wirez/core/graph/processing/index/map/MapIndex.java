package org.wirez.core.graph.processing.index.map;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.processing.index.Index;

import java.util.Map;

public class MapIndex implements Index<Node, Edge> {
    
    final Map<String, Node> nodes;
    final Map<String, Edge> edges;

    public MapIndex(final Map<String, Node> nodes,
                    final Map<String, Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    @Override
    public Element get(final String uuid) {
        Element node = nodes.get(uuid);
        if ( null == node ) {
            return edges.get(uuid);
        }
        return node;
    }

    @Override
    public Node getNode(final String uuid) {
        return nodes.get(uuid);
    }

    @Override
    public Edge getEdge(final String uuid) {
        return edges.get(uuid);
    }

    @Override
    public void clear() {
        nodes.clear();
        edges.clear();
    }

}
