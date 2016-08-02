package org.wirez.core.registry.impl;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.diagram.Diagram;
import org.wirez.core.factory.Factory;
import org.wirez.core.registry.RegistryFactory;
import org.wirez.core.registry.definition.AdapterRegistry;
import org.wirez.core.registry.definition.TypeDefinitionRegistry;
import org.wirez.core.registry.definition.TypeDefinitionSetRegistry;
import org.wirez.core.registry.diagram.DiagramRegistry;
import org.wirez.core.registry.factory.FactoryRegistry;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AbstractRegistryFactory implements RegistryFactory {

    private DefinitionManager definitionManager;

    protected AbstractRegistryFactory() {
    }

    public AbstractRegistryFactory( final DefinitionManager definitionManager ) {
        this.definitionManager = definitionManager;
    }

    @Override
    public AdapterRegistry newAdapterRegistry() {
        return new AdapterRegistryImpl();
    }

    @Override
    public <T> TypeDefinitionSetRegistry<T> newDefinitionSetRegistry() {
        return new DefinitionSetMapRegistry<T>( definitionManager );
    }

    @Override
    public <T> TypeDefinitionRegistry<T> newDefinitionRegistry() {
        return new DefinitionMapRegistry<T>( definitionManager );
    }

    @Override
    public <T extends Factory<?, ?>> FactoryRegistry<T> newFactoryRegistry() {
        return new FactoryRegistryImpl<T>( definitionManager );
    }

    @Override
    public <T extends Diagram> DiagramRegistry<T> newDiagramRegistry() {
        return new DiagramListRegistry<T>();
    }

    public <T> MapRegistry<T> newMapRegistry( final KeyProvider<T> keyProvider ) {
        return new MapRegistry<T>( keyProvider, new HashMap<String, T>() );
    }

    public <T> MapRegistry<T> newMapRegistry( final KeyProvider<T> keyProvider,
                                              final java.util.Map<String, T> map ) {
        return new MapRegistry<T>( keyProvider, map );
    }

    public <T> ListRegistry<T> newListRegistry( final KeyProvider<T> keyProvider ) {
        return new ListRegistry<T>( keyProvider, new ArrayList<T>() );
    }

    public <T> ListRegistry<T> newListRegistry( final KeyProvider<T> keyProvider,
                                              final java.util.List<T> list ) {
        return new ListRegistry<T>( keyProvider, list );
    }

}
