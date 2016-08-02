package org.wirez.core.api;

import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.diagram.Diagram;
import org.wirez.core.diagram.DiagramImpl;
import org.wirez.core.diagram.Settings;
import org.wirez.core.diagram.SettingsImpl;
import org.wirez.core.factory.definition.DefinitionFactory;
import org.wirez.core.factory.graph.ElementFactory;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.Graph;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.definition.Definition;
import org.wirez.core.graph.content.view.*;
import org.wirez.core.registry.RegistryFactory;
import org.wirez.core.registry.factory.FactoryRegistry;
import org.wirez.core.util.UUID;

import java.util.Iterator;

public abstract class AbstractFactoryManager implements FactoryManager {

    private final FactoryRegistry factoryRegistry;
    private final DefinitionManager definitionManager;

    protected AbstractFactoryManager() {
        this.factoryRegistry = null;
        this.definitionManager = null;
    }

    public AbstractFactoryManager( final RegistryFactory registryFactory,
                                    final DefinitionManager definitionManager ) {
        this.factoryRegistry = registryFactory.newFactoryRegistry();
        this.definitionManager = definitionManager;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public <T> T newDefinition( final String id ) {
        final DefinitionFactory<T> factory = factoryRegistry.getDefinitionFactory( id );
        return factory.build( id );
    }

    @Override
    public <T> T newDefinition( final Class<T> type ) {
        final String id = BindableAdapterUtils.getDefinitionId( type, definitionManager );
        return newDefinition( id );
    }

    @Override
    public Element newElement( final String uuid,
                               final String id ) {
        return doBuild( uuid, id );
    }

    @Override
    public Element newElement( final String uuid,
                               final Class<?> type ) {
        final String id = BindableAdapterUtils.getGenericClassName( type );
        return newElement( uuid, id );
    }

    private Object getDefinitionSet( final String id ) {
        return definitionManager.definitionSets().getDefinitionSetById( id );
    }


    @Override
    @SuppressWarnings( "unchecked" )
    public <D extends Diagram> D newDiagram( final String uuid,
                                             final String id ) {

        final Graph graph = ( Graph ) newElement( uuid, id );

        final Settings diagramSettings = new SettingsImpl( id );

        final String rootId = getCanvasRoot( graph );

        if ( null != rootId ) {

            diagramSettings.setCanvasRootUUID( rootId );

        }

        Diagram diagram = new DiagramImpl( uuid, graph, diagramSettings );

        return ( D ) diagram;
    }

    @Override
    public <D extends Diagram> D newDiagram( final String uuid,
                                             final Class<?> type ) {
        final String id = BindableAdapterUtils.getDefinitionSetId( type, definitionManager );

        return newDiagram( uuid, id );
    }

    @Override
    public FactoryRegistry registry() {
        return factoryRegistry;
    }

    @SuppressWarnings( "unchecked" )
    private <T, C extends Definition<T>> Element<C>  doBuild ( final String uuid,
                                                               final String definitionId ) {

        final Object defSet = getDefinitionSet( definitionId );

        final boolean isDefSet = null != defSet;

        final Object definition = isDefSet ? defSet : newDefinition( definitionId );

        final Class<? extends ElementFactory> factoryType = isDefSet ?
                definitionManager.adapters().forDefinitionSet().getGraphFactoryType( definition ) :
                definitionManager.adapters().forDefinition().getGraphFactoryType( definition );

        final ElementFactory<Definition<Object>, Element<Definition<Object>>> factory = getGraphFactory( factoryType );

        final Element<Definition<Object>> element = factory.build( uuid, definition );

        return ( Element<C> ) element;
    }

    @SuppressWarnings( "unchecked" )
    private ElementFactory<Definition<Object>, Element<Definition<Object>>> getGraphFactory( final Class<? extends ElementFactory> type ) {

        return factoryRegistry.getGraphFactory( type );

    }


    // TODO: Refactor this - do not apply by default this behavior?
    private String getCanvasRoot( final Graph graph ) {

        final Node view = getFirstGraphViewNode( graph );

        if ( null != view ) {

            return view.getUUID();

        }

        return null;
    }

    @SuppressWarnings("unchecked")
    private Node getFirstGraphViewNode( final Graph graph ) {

        if ( null != graph ) {
            Iterable<Node> nodesIterable = graph.nodes();
            if ( null != nodesIterable ) {
                Iterator<Node> nodesIt = nodesIterable.iterator();
                if ( null != nodesIt ) {
                    while ( nodesIt.hasNext() ) {
                        Node node = nodesIt.next();
                        Object content = node.getContent();
                        if ( content instanceof View) {
                            return node;
                        }

                    }
                }
            }

        }

        return null;
    }

}
