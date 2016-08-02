package org.wirez.core.factory.impl;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.factory.graph.ElementFactory;
import org.wirez.core.factory.graph.NodeFactory;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.graph.content.view.ViewImpl;
import org.wirez.core.graph.impl.NodeImpl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Set;

@ApplicationScoped
public class NodeFactoryImpl extends AbstractElementFactory<Object, Node<Object, Edge>> implements NodeFactory<Object> {

    private final DefinitionManager definitionManager;

    protected NodeFactoryImpl() {
        this.definitionManager = null;
    }

    @Inject
    public NodeFactoryImpl( final DefinitionManager definitionManager ) {
        this.definitionManager = definitionManager;
    }

    @Override
    public Node<Object, Edge> build( final String uuid ) {
        return build( uuid, null );
    }

    @Override
    public Class<? extends ElementFactory> getFactoryType() {
        return NodeFactory.class;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public Node<Object, Edge> build( final String uuid,
                                     final Object definition ) {

        final NodeImpl node = new NodeImpl<>( uuid );

        if ( null != definition ) {

            View<Object> content = new ViewImpl<>( definition, buildBounds() );
            node.setContent( content );
            node.getLabels().addAll( getLabels( definition ) );
        }

        return node;
    }

    private Set<String> getLabels( final Object definition ) {
        return definitionManager.adapters().forDefinition().getLabels( definition );
    }

}
