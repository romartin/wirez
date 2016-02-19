package org.wirez.core.api.graph.processing.index.map;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ParentChildRelationship;
import org.wirez.core.api.graph.processing.index.GraphIndex;

import java.util.*;

public class MapGraphIndex implements GraphIndex<Node, Edge> {
    
    private final Map<String, Node> nodes;
    private final Map<String, Edge> edges;

    public MapGraphIndex(final Map<String, Node> nodes, 
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
    public Node getParent(final String uuid) {
        final Node node = nodes.get(uuid);
        if ( null != node ) {
            final List<Edge> edges = node.getOutEdges();
            if ( null != edges && !edges.isEmpty() ) {
                for (final Edge edge : edges) {
                    final Object content = edge.getContent();
                    if ( content instanceof ParentChildRelationship) {
                        final Node child = edge.getTargetNode();
                        if ( null != child && child.getUUID().equals(uuid) ) {
                            return node;
                        }

                    }
                }
            }

        }
        
        return null;
    }

    @Override
    public Collection<Node> getChildren(final String uuid) {
        final Node node = nodes.get(uuid);
        if ( null != node ) {
            final List<Edge> edges = node.getOutEdges();
            if ( null != edges && !edges.isEmpty() ) {
                final List<Node> result = new ArrayList<>();
                for (final Edge edge : edges) {
                    final Object content = edge.getContent();
                    if ( content instanceof ParentChildRelationship ) {
                        final Node child = edge.getTargetNode();
                        result.add(child);

                    }
                }
                return result;
            }
        }
        
        return null;        
    }

    @Override
    public Collection<Node> findNodes(final List<String> labels) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Edge> findEdges(final List<String> labels) {
        throw new UnsupportedOperationException();
    }
    
}
