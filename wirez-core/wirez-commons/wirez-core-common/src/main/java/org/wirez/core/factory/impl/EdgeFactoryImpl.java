package org.wirez.core.factory.impl;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.factory.graph.EdgeFactory;
import org.wirez.core.factory.graph.ElementFactory;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.ViewConnector;
import org.wirez.core.graph.content.view.ViewConnectorImpl;
import org.wirez.core.graph.impl.EdgeImpl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Set;

@ApplicationScoped
public class EdgeFactoryImpl extends AbstractElementFactory<Object, Edge<Object, Node>> implements EdgeFactory<Object> {

    private final DefinitionManager definitionManager;

    protected EdgeFactoryImpl() {
        this.definitionManager = null;
    }

    @Inject
    public EdgeFactoryImpl( final DefinitionManager definitionManager ) {
        this.definitionManager = definitionManager;
    }

    @Override
    public Edge<Object, Node> build( final String uuid ) {
        return build( uuid, null );
    }

    @Override
    public Class<? extends ElementFactory> getFactoryType() {
        return EdgeFactory.class;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public Edge<Object, Node> build( final String uuid,
                                     final Object definition ) {

        final EdgeImpl edge = new EdgeImpl<>( uuid );

        if ( null != definition ) {

            ViewConnector<Object> content = new ViewConnectorImpl<>( definition, buildBounds() );
            edge.setContent( content );
            edge.getLabels().addAll( getLabels( definition ) );
        }

        return edge;
    }

    private Set<String> getLabels( final Object definition ) {
        return definitionManager.adapters().forDefinition().getLabels( definition );
    }

}
