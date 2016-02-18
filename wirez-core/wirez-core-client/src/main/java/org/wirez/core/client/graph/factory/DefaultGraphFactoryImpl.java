package org.wirez.core.client.graph.factory;

import org.wirez.core.api.definition.DefaultDefinition;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.graph.Bounds;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.content.ViewContentImpl;
import org.wirez.core.api.graph.factory.BaseElementFactory;
import org.wirez.core.api.graph.factory.GraphFactory;
import org.wirez.core.api.graph.impl.BoundImpl;
import org.wirez.core.api.graph.impl.BoundsImpl;
import org.wirez.core.api.graph.impl.GraphImpl;
import org.wirez.core.api.graph.store.GraphNodeStoreImpl;

import javax.enterprise.context.ApplicationScoped;
import java.util.Set;

@ApplicationScoped
public class DefaultGraphFactoryImpl extends BaseElementFactory<DefaultDefinition, ViewContent<DefaultDefinition>, Graph<ViewContent<DefaultDefinition>, Node>> 
        implements GraphFactory<DefaultDefinition> {

    @Override
    public Graph<ViewContent<DefaultDefinition>, Node> build(String uuid, DefaultDefinition definition, Set<Property> properties, Set<String> labels) {
        Graph<ViewContent<DefaultDefinition>, Node> graph =
                new GraphImpl<ViewContent<DefaultDefinition>>( uuid,
                        properties,
                        labels,
                        new ViewContentImpl<DefaultDefinition>( definition, buildBounds()),
                        new GraphNodeStoreImpl());

        return graph;
        
    }

    protected Bounds buildBounds() {
        return new BoundsImpl(new BoundImpl(1000d, 1000d), new BoundImpl(0d, 0d));
    }

}
