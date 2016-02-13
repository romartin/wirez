package org.wirez.core.backend.graph.factory;

import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.content.ViewContentImpl;
import org.wirez.core.api.graph.factory.BaseElementFactory;
import org.wirez.core.api.graph.factory.DefaultGraphFactory;
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.graph.impl.DefaultGraphImpl;
import org.wirez.core.api.graph.store.DefaultGraphEdgeStore;
import org.wirez.core.api.graph.store.DefaultGraphNodeStore;
import org.wirez.core.api.util.UUID;

import javax.enterprise.context.Dependent;
import java.util.Set;

@Dependent
public class DefaultGraphFactoryImpl<W extends Definition> extends BaseElementFactory<W, ViewContent<W>, DefaultGraph<ViewContent<W>, Node, Edge>> 
        implements DefaultGraphFactory<W> {

    @Override
    public DefaultGraph<ViewContent<W>, Node, Edge> build(String uuid, W definition, Set<Property> properties, Set<String> labels) {

        DefaultGraph<ViewContent<W>, Node, Edge> graph =
                new DefaultGraphImpl<ViewContent<W>>( uuid,
                        properties,
                        labels,
                        new ViewContentImpl<>( definition, buildBounds()),
                        new DefaultGraphNodeStore(),
                        new DefaultGraphEdgeStore());
        
        return graph;
    }
}
