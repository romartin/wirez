package org.wirez.core.graph.factory;

import org.wirez.core.graph.Graph;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.definition.DefinitionSet;
import org.wirez.core.graph.content.definition.DefinitionSetImpl;
import org.wirez.core.graph.impl.GraphImpl;
import org.wirez.core.graph.store.GraphNodeStoreImpl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.Set;

@ApplicationScoped
@Named( GraphFactoryImpl.FACTORY_NAME )
public class GraphFactoryImpl extends BaseGraphFactory<DefinitionSet, Graph<DefinitionSet, Node>> {

    public static final String FACTORY_NAME = "graphFactoryImpl";
    
    protected GraphFactoryImpl() {
    }
    
    @Override
    public Graph<DefinitionSet, Node> build(String uuid, String definitionSetId, Set<String> labels) {
        Graph<DefinitionSet, Node> graph =
                new GraphImpl<DefinitionSet>( uuid,
                        labels,
                        new DefinitionSetImpl( definitionSetId ),
                        new GraphNodeStoreImpl());

        return graph;
    }
}
