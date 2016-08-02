package org.wirez.core.factory.impl;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.factory.graph.ElementFactory;
import org.wirez.core.factory.graph.GraphFactory;
import org.wirez.core.graph.Graph;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.definition.DefinitionSet;
import org.wirez.core.graph.content.definition.DefinitionSetImpl;
import org.wirez.core.graph.impl.GraphImpl;
import org.wirez.core.graph.store.GraphNodeStoreImpl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class GraphFactoryImpl extends AbstractElementFactory<Object, Graph<Object, Node>> implements GraphFactory<Object> {

    private final DefinitionManager definitionManager;

    protected GraphFactoryImpl() {
        this.definitionManager = null;
    }

    @Inject
    public GraphFactoryImpl( final DefinitionManager definitionManager ) {
        this.definitionManager = definitionManager;
    }

    @Override
    public Graph<Object, Node> build( final String uuid ) {
        return build( uuid, null );
    }

    @Override
    public Class<? extends ElementFactory> getFactoryType() {
        return GraphFactory.class;
    }

    @Override
    @SuppressWarnings( "unchecked   " )
    public Graph<Object, Node> build( final String uuid,
                                      final Object definitionSet ) {

        final GraphImpl graph = new GraphImpl<>( uuid, new GraphNodeStoreImpl() );
        final String id = getId( definitionSet );
        final DefinitionSet content = new DefinitionSetImpl( id );
        graph.setContent( content );

        return graph;
    }

    private String getId( final Object defSet ) {
        return definitionManager.adapters().forDefinitionSet().getId( defSet );
    }

}
