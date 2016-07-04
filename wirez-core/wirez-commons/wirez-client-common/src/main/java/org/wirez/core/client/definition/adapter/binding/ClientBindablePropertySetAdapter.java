package org.wirez.core.client.definition.adapter.binding;

import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.definition.adapter.binding.BindablePropertySetAdapter;

import java.util.Map;
import java.util.Set;

class ClientBindablePropertySetAdapter extends AbstractClientBindableAdapter<Object> implements BindablePropertySetAdapter<Object> {

    private Map<Class, String> propertyNameFieldNames;
    private Map<Class, Set<String>> propertiesFieldNames;

    @Override
    public void setBindings( final Map<Class, String> propertyNameFieldNames, 
                             final Map<Class, Set<String>> propertiesFieldNames) {
        this.propertyNameFieldNames = propertyNameFieldNames;
        this.propertiesFieldNames = propertiesFieldNames;
    }
    
    @Override
    public String getId(final Object pojo) {
        return BindableAdapterUtils.getPropertySetId( pojo.getClass() );
    }

    @Override
    public String getName(final Object pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getProxiedValue( pojo, getPropertyNameFieldNames().get( clazz ) );
    }

    @Override
    public Set<?> getProperties(final Object pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getProxiedSet( pojo, getPropertiesFieldNames().get( clazz ) );
    }

    @Override
    public boolean accepts(final Class<?> pojoClass) {
        if ( null != propertyNameFieldNames ) {
            final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojoClass  );
            return getPropertyNameFieldNames().containsKey( clazz );
        }
        return false;
    }

    private Map<Class, String> getPropertyNameFieldNames() {
        return propertyNameFieldNames;
    }
    
    private Map<Class, Set<String>> getPropertiesFieldNames() {
        return propertiesFieldNames;
    }
    
}
