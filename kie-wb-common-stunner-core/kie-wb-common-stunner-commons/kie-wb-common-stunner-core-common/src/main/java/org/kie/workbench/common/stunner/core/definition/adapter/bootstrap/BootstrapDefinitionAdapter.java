package org.kie.workbench.common.stunner.core.definition.adapter.bootstrap;

import org.kie.workbench.common.stunner.core.definition.adapter.DefinitionAdapter;
import org.kie.workbench.common.stunner.core.factory.graph.ElementFactory;
import org.kie.workbench.common.stunner.core.registry.definition.AdapterRegistry;

import java.util.Set;

class BootstrapDefinitionAdapter implements DefinitionAdapter<Object> {

    private final AdapterRegistry adapterRegistry;

    BootstrapDefinitionAdapter( final AdapterRegistry adapterRegistry ) {
        this.adapterRegistry = adapterRegistry;
    }

    @Override
    public String getId( final Object pojo ) {
        return getWrapped( pojo ).getId( pojo );
    }

    @Override
    public Object getNameProperty( final Object pojo ) {
        return getWrapped( pojo ).getNameProperty( pojo );
    }

    @Override
    public String getCategory( final Object pojo ) {
        return getWrapped( pojo ).getCategory( pojo );
    }

    @Override
    public String getTitle( final Object pojo ) {
        return getWrapped( pojo ).getTitle( pojo );
    }

    @Override
    public String getDescription( final Object pojo ) {
        return getWrapped( pojo ).getDescription( pojo );
    }

    @Override
    public Set<String> getLabels( final Object pojo ) {
        return getWrapped( pojo ).getLabels( pojo );
    }

    @Override
    public Set<?> getPropertySets( final Object pojo ) {
        return getWrapped( pojo ).getPropertySets( pojo );
    }

    @Override
    public Set<?> getProperties( final Object pojo ) {
        return getWrapped( pojo ).getProperties( pojo );
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

    private DefinitionAdapter<Object> getWrapped( final Object pojo ) {
        return getWrapped( pojo.getClass() );
    }

    private DefinitionAdapter<Object> getWrapped( final Class<?> type ) {
        return adapterRegistry.getDefinitionAdapter( type );
    }
    
}
