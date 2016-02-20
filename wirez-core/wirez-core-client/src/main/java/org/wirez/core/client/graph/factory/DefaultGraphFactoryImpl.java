package org.wirez.core.client.graph.factory;

import org.wirez.core.api.definition.DefaultDefinition;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.graph.content.view.Bounds;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.graph.content.view.ViewImpl;
import org.wirez.core.api.graph.factory.BaseElementFactory;
import org.wirez.core.api.graph.factory.GraphFactory;
import org.wirez.core.api.graph.content.view.BoundImpl;
import org.wirez.core.api.graph.content.view.BoundsImpl;
import org.wirez.core.api.graph.impl.GraphImpl;
import org.wirez.core.api.graph.store.GraphNodeStoreImpl;

import javax.enterprise.context.ApplicationScoped;
import java.util.Set;

@ApplicationScoped
public class DefaultGraphFactoryImpl extends BaseElementFactory<DefaultDefinition, View<DefaultDefinition>, Graph<View<DefaultDefinition>, Node>> 
        implements GraphFactory<DefaultDefinition> {

    @Override
    public Graph<View<DefaultDefinition>, Node> build(String uuid, DefaultDefinition definition, Set<Property> properties, Set<String> labels) {
        Graph<View<DefaultDefinition>, Node> graph =
                new GraphImpl<View<DefaultDefinition>>( uuid,
                        properties,
                        labels,
                        new ViewImpl<DefaultDefinition>( definition, buildBounds()),
                        new GraphNodeStoreImpl());

        return graph;
        
    }

    protected Bounds buildBounds() {
        return new BoundsImpl(new BoundImpl(1000d, 1000d), new BoundImpl(0d, 0d));
    }

}
