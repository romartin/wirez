package org.kie.workbench.common.stunner.core.definition.adapter.bootstrap;

import org.kie.workbench.common.stunner.core.definition.adapter.PropertyAdapter;
import org.kie.workbench.common.stunner.core.definition.property.PropertyType;
import org.kie.workbench.common.stunner.core.registry.definition.AdapterRegistry;

import java.util.Map;

class BootstrapPropertyAdapter implements PropertyAdapter<Object, Object> {

    private final AdapterRegistry adapterRegistry;

    BootstrapPropertyAdapter( final AdapterRegistry adapterRegistry ) {
        this.adapterRegistry = adapterRegistry;
    }

    @Override
    public String getId( final Object pojo ) {
        return getWrapped( pojo ).getId( pojo );
    }

    @Override
    public PropertyType getType( final Object pojo ) {
        return getWrapped( pojo ).getType( pojo );
    }

    @Override
    public String getCaption( final Object pojo ) {
        return getWrapped( pojo ).getCaption( pojo );
    }

    @Override
    public String getDescription( final Object pojo ) {
        return getWrapped( pojo ).getDescription( pojo );
    }

    @Override
    public boolean isReadOnly( final Object pojo ) {
        return getWrapped( pojo ).isReadOnly( pojo );
    }

    @Override
    public boolean isOptional( final Object pojo ) {
        return getWrapped( pojo ).isOptional( pojo );
    }

    @Override
    public Object getValue( final Object pojo ) {
        return getWrapped( pojo ).getValue( pojo );
    }

    @Override
    public Object getDefaultValue( final Object pojo ) {
        return getWrapped( pojo ).getDefaultValue( pojo );
    }

    @Override
    public Map<Object, String> getAllowedValues( final Object pojo ) {
        return getWrapped( pojo ).getAllowedValues( pojo );
    }

    @Override
    public void setValue( final Object pojo,
                          final Object value ) {
        getWrapped( pojo ).setValue( pojo, value );
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

    private PropertyAdapter<Object, Object> getWrapped( final Object pojo ) {
        return getWrapped( pojo.getClass() );
    }

    @SuppressWarnings( "unchecked" )
    private PropertyAdapter<Object, Object> getWrapped( final Class<?> type ) {
        return ( PropertyAdapter<Object, Object> ) adapterRegistry.getPropertyAdapter( type );
    }

}
