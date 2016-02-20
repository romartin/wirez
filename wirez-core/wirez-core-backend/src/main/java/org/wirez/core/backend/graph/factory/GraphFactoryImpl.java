package org.wirez.core.backend.graph.factory;

import org.wirez.core.api.definition.Definition;
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

import javax.enterprise.context.Dependent;
import java.util.Set;

@Dependent
public class GraphFactoryImpl<W extends Definition> extends BaseElementFactory<W, View<W>, Graph<View<W>, Node>> 
        implements GraphFactory<W> {

    @Override
    public Graph<View<W>, Node> build(String uuid, W definition, Set<Property> properties, Set<String> labels) {

        Graph<View<W>, Node> graph =
                new GraphImpl<View<W>>( uuid,
                        properties,
                        labels,
                        new ViewImpl<>( definition, buildBounds()),
                        new GraphNodeStoreImpl());
        
        return graph;
    }

    protected Bounds buildBounds() {
        return new BoundsImpl(new BoundImpl(1000d, 1000d), new BoundImpl(0d, 0d));
    }

}
