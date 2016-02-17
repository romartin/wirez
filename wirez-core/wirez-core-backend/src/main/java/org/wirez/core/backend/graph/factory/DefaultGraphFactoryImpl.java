package org.wirez.core.backend.graph.factory;

import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.graph.Bounds;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.content.ViewContentImpl;
import org.wirez.core.api.graph.factory.BaseElementFactory;
import org.wirez.core.api.graph.factory.DefaultGraphFactory;
import org.wirez.core.api.graph.impl.BoundImpl;
import org.wirez.core.api.graph.impl.BoundsImpl;
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.graph.impl.GraphImpl;
import org.wirez.core.api.graph.store.DefaultGraphEdgeStore;
import org.wirez.core.api.graph.store.GraphNodeStoreImpl;

import javax.enterprise.context.Dependent;
import java.util.Set;

@Dependent
public class DefaultGraphFactoryImpl<W extends Definition> extends BaseElementFactory<W, ViewContent<W>, DefaultGraph<ViewContent<W>, Node, Edge>> 
        implements DefaultGraphFactory<W> {

    @Override
    public DefaultGraph<ViewContent<W>, Node, Edge> build(String uuid, W definition, Set<Property> properties, Set<String> labels) {

        DefaultGraph<ViewContent<W>, Node, Edge> graph =
                new GraphImpl<ViewContent<W>>( uuid,
                        properties,
                        labels,
                        new ViewContentImpl<>( definition, buildBounds()),
                        new GraphNodeStoreImpl(),
                        new DefaultGraphEdgeStore());
        
        return graph;
    }

    protected Bounds buildBounds() {
        return new BoundsImpl(new BoundImpl(1000d, 1000d), new BoundImpl(0d, 0d));
    }

}
