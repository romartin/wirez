package org.wirez.core.registry.impl;

import org.wirez.core.definition.adapter.AdapterManager;
import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.factory.Factory;
import org.wirez.core.factory.definition.DefinitionFactory;
import org.wirez.core.factory.graph.ElementFactory;
import org.wirez.core.registry.factory.TypeFactoryRegistry;

import java.util.*;

class FactoryRegistryImpl<T extends Factory<?, ?>> implements TypeFactoryRegistry<T> {

    private final AdapterManager adapterManager;
    private final List<DefinitionFactory<?>> definitionFactories = new LinkedList<>();
    private final Map<Class<? extends ElementFactory>, ElementFactory<?, ?>> graphFactories =
            new HashMap<>();

    FactoryRegistryImpl( final AdapterManager adapterManager ) {
        this.adapterManager = adapterManager;
    }

    @Override
    public DefinitionFactory<?> getDefinitionFactory( final String id ) {

        for ( final DefinitionFactory<?> factory : definitionFactories ) {

            if ( factory.accepts( id ) ) {

                return factory;

            }

        }

        return null;
    }

    @Override
    public ElementFactory<?, ?> getGraphFactory( final Class<? extends ElementFactory> type ) {

        return graphFactories.get( type );

    }

    @Override
    @SuppressWarnings( "unchecked" )
    public void register( final T item ) {

        if ( item instanceof DefinitionFactory ) {

            definitionFactories.add( ( DefinitionFactory<?> ) item );

        } else if ( item instanceof ElementFactory ){

            graphFactories.put( ( ( ElementFactory ) item ).getFactoryType(),  ( ElementFactory<?, ?> ) item );

        }

    }

    @Override
    public boolean remove( final T item ) {

        if ( item instanceof DefinitionFactory ) {

            return definitionFactories.remove( item );

        } else if ( item instanceof ElementFactory ) {

            return null != graphFactories.remove( item.getClass() );

        }

        return false;
    }

    @Override
    public void clear() {
        definitionFactories.clear();
        graphFactories.clear();
    }

    @Override
    public boolean contains( final T item ) {

        if ( item instanceof DefinitionFactory ) {

            return definitionFactories.contains( item );

        } else if ( item instanceof ElementFactory ) {

            return graphFactories.containsValue( item );

        }

        return false;

    }

    @Override
    @SuppressWarnings( "unchecked" )
    public Collection<T> getItems() {
        return new LinkedList<T>() {{
            addAll( ( Collection<? extends T> ) definitionFactories );
            addAll( ( Collection<? extends T> ) graphFactories.values() );
        }};
    }

    @Override
    public DefinitionFactory<?> getDefinitionFactory( final Class<?> type ) {
        final String id = BindableAdapterUtils.getDefinitionId( type, adapterManager.registry() );
        return getDefinitionFactory( id );
    }

}
