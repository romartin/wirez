package org.wirez.core.api.graph.factory;

import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.*;
import org.wirez.core.api.graph.impl.GraphImpl;
import org.wirez.core.api.graph.store.GraphNodeStoreImpl;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import java.util.Set;

@Dependent
@Named( GraphFactoryImpl.FACTORY_NAME )
public class GraphFactoryImpl extends BaseElementFactory<Definition, View<Definition>, Graph<View<Definition>, Node>> 
        implements GraphFactory<Definition> {

    public static final String FACTORY_NAME = "graphFactoryImpl";
    
    protected GraphFactoryImpl() {
    }

    @Override
    public Graph<View<Definition>, Node> build(String uuid, Definition definition, Set<Property> properties, Set<String> labels) {

        Graph<View<Definition>, Node> graph =
                new GraphImpl<View<Definition>>( uuid,
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
