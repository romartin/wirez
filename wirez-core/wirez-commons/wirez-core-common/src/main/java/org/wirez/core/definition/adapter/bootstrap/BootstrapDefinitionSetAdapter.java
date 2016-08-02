package org.wirez.core.definition.adapter.bootstrap;

import org.wirez.core.definition.adapter.DefinitionSetAdapter;
import org.wirez.core.factory.graph.ElementFactory;
import org.wirez.core.graph.Graph;
import org.wirez.core.registry.definition.AdapterRegistry;

import java.util.Set;

class BootstrapDefinitionSetAdapter implements DefinitionSetAdapter<Object> {

    private final AdapterRegistry adapterRegistry;

    BootstrapDefinitionSetAdapter( final AdapterRegistry adapterRegistry ) {
        this.adapterRegistry = adapterRegistry;
    }

    @Override
    public String getId( final Object pojo ) {
        return getWrapped( pojo ).getId( pojo );
    }

    @Override
    public String getDomain( final Object pojo ) {
        return getWrapped( pojo ).getDomain( pojo );
    }

    @Override
    public String getDescription( final Object pojo ) {
        return getWrapped( pojo ).getDescription( pojo );
    }

    @Override
    public Set<String> getDefinitions( final Object pojo ) {
        return getWrapped( pojo ).getDefinitions( pojo );
    }

    @Override
    public Class<? extends ElementFactory> getGraphFactoryType( final Object pojo ) {
        return getWrapped( pojo ).getGraphFactoryType( pojo );
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public boolean accepts( final Class<?> type ) {
        return null != getWrapped( type );
    }

    @Override
    public boolean isPojoModel() {
        return false;
    }

    private DefinitionSetAdapter<Object> getWrapped( final Object pojo ) {
        return getWrapped( pojo.getClass() );
    }

    private DefinitionSetAdapter<Object> getWrapped( final Class<?> type ) {
        return adapterRegistry.getDefinitionSetAdapter( type );
    }
}
