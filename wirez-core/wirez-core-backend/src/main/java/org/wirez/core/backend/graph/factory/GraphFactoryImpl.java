package org.wirez.core.backend.graph.factory;

import org.wirez.core.api.definition.Definition;
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

import javax.enterprise.context.Dependent;
import java.util.Set;

@Dependent
public class GraphFactoryImpl<W extends Definition> extends BaseElementFactory<W, ViewContent<W>, Graph<ViewContent<W>, Node>> 
        implements GraphFactory<W> {

    @Override
    public Graph<ViewContent<W>, Node> build(String uuid, W definition, Set<Property> properties, Set<String> labels) {

        Graph<ViewContent<W>, Node> graph =
                new GraphImpl<ViewContent<W>>( uuid,
                        properties,
                        labels,
                        new ViewContentImpl<>( definition, buildBounds()),
                        new GraphNodeStoreImpl());
        
        return graph;
    }

    protected Bounds buildBounds() {
        return new BoundsImpl(new BoundImpl(1000d, 1000d), new BoundImpl(0d, 0d));
    }

}
