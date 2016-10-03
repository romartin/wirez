package org.kie.workbench.common.stunner.core.graph.processing.index.map;

import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Graph;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.processing.index.IncrementalIndexBuilder;

import javax.enterprise.context.Dependent;

@Dependent
public class IncrementalMapIndexBuilder extends MapIndexBuilder 
    implements IncrementalIndexBuilder<Graph<?, Node>,
            Node, Edge, MapIndex> {
    
    @Override
    public IncrementalIndexBuilder<Graph<?, Node>, Node, Edge, MapIndex> addNode(final MapIndex index,
                                                                                 final Node node) {
        assert index != null && node != null;
        index.nodes.put(node.getUUID(), node);
        return this;
    }

    @Override
    public IncrementalIndexBuilder<Graph<?, Node>, Node, Edge, MapIndex> removeNode(final MapIndex index,
                                                                                final Node node) {
        assert index != null && node != null;
        index.nodes.remove(node.getUUID());
        return this;
    }

    @Override
    public IncrementalIndexBuilder<Graph<?, Node>, Node, Edge, MapIndex> addEdge(final MapIndex index, 
                                                                             final Edge edge) {
        assert index != null && edge != null;
        index.edges.put(edge.getUUID(), edge);
        return this;
    }

    @Override
    public IncrementalIndexBuilder<Graph<?, Node>, Node, Edge, MapIndex> removeEdge(final MapIndex index,
                                                                                final Edge edge) {
        assert index != null && edge != null;
        index.edges.remove(edge.getUUID());
        return this;
    }

    @Override
    public IncrementalIndexBuilder<Graph<?, Node>, Node, Edge, MapIndex> clear(MapIndex index) {
        assert index != null;
        index.nodes.clear();
        index.edges.clear();
        return this;
    }

}
