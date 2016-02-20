package org.wirez.core.api.graph.processing.index.map;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.processing.index.Index;

import java.util.*;

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

}
