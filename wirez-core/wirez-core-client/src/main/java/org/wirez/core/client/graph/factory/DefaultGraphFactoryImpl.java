package org.wirez.core.client.graph.factory;

import org.wirez.core.api.definition.DefaultDefinition;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.graph.Bounds;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.content.ViewContentImpl;
import org.wirez.core.api.graph.factory.BaseElementFactory;
import org.wirez.core.api.graph.factory.DefaultGraphFactory;
import org.wirez.core.api.graph.impl.DefaultBound;
import org.wirez.core.api.graph.impl.DefaultBounds;
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.graph.impl.DefaultGraphImpl;
import org.wirez.core.api.graph.store.DefaultGraphEdgeStore;
import org.wirez.core.api.graph.store.DefaultGraphNodeStore;
import org.wirez.core.api.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import java.util.Set;

@ApplicationScoped
public class DefaultGraphFactoryImpl extends BaseElementFactory<DefaultDefinition, ViewContent<DefaultDefinition>, DefaultGraph<ViewContent<DefaultDefinition>, Node, Edge>> 
        implements DefaultGraphFactory<DefaultDefinition> {

    @Override
    public DefaultGraph<ViewContent<DefaultDefinition>, Node, Edge> build(String uuid, DefaultDefinition definition, Set<Property> properties, Set<String> labels) {
        DefaultGraph<ViewContent<DefaultDefinition>, Node, Edge> graph =
                new DefaultGraphImpl<ViewContent<DefaultDefinition>>( uuid,
                        properties,
                        labels,
                        new ViewContentImpl<DefaultDefinition>( definition, buildBounds()),
                        new DefaultGraphNodeStore(),
                        new DefaultGraphEdgeStore());

        return graph;
        
    }

    protected Bounds buildBounds() {
        return new DefaultBounds(new DefaultBound(1000d, 1000d), new DefaultBound(0d, 0d));
    }

}
