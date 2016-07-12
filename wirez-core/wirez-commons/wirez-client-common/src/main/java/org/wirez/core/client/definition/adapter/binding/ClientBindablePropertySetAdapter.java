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
        return getProxiedValue( pojo, getPropertyNameFieldNames().get( pojo.getClass() ) );
    }

    @Override
    public Set<?> getProperties(final Object pojo) {
        return getProxiedSet( pojo, getPropertiesFieldNames().get( pojo.getClass() ) );
    }

    @Override
    public boolean accepts(final Class<?> pojoClass) {
        if ( null != propertyNameFieldNames ) {
            return getPropertyNameFieldNames().containsKey( pojoClass );
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
