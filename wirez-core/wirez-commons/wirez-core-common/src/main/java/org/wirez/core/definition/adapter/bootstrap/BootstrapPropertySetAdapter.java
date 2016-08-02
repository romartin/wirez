package org.wirez.core.definition.adapter.bootstrap;

import org.wirez.core.definition.adapter.PropertySetAdapter;
import org.wirez.core.registry.definition.AdapterRegistry;

import java.util.Set;

class BootstrapPropertySetAdapter implements PropertySetAdapter<Object> {

    private final AdapterRegistry adapterRegistry;

    BootstrapPropertySetAdapter( final AdapterRegistry adapterRegistry ) {
        this.adapterRegistry = adapterRegistry;
    }

    @Override
    public String getId( final Object pojo ) {
        return getWrapped( pojo ).getId( pojo );
    }

    @Override
    public String getName( final Object pojo ) {
        return getWrapped( pojo ).getName( pojo );
    }

    @Override
    public Set<?> getProperties( final Object pojo ) {
        return getWrapped( pojo ).getProperties( pojo );
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

    private PropertySetAdapter<Object> getWrapped( final Object pojo ) {
        return getWrapped( pojo.getClass() );
    }

    private PropertySetAdapter<Object> getWrapped( final Class<?> type ) {
        return adapterRegistry.getPropertySetAdapter( type );
    }

}
