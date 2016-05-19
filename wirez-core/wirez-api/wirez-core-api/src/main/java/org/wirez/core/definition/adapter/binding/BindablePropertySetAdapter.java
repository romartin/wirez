package org.wirez.core.definition.adapter.binding;

import org.wirez.core.definition.adapter.PropertySetAdapter;

import java.util.Map;
import java.util.Set;

public abstract class BindablePropertySetAdapter<T> extends AbstractBindableAdapter<T> implements PropertySetAdapter<T> {

    protected abstract Map<Class, String> getPropertyNameFieldNames();
    protected abstract Map<Class, Set<String>> getPropertiesFieldNames();
    
    @Override
    public String getId(final T pojo) {
        return BindableAdapterUtils.getPropertySetId( pojo.getClass() );
    }

    @Override
    public String getName(final T pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getProxiedValue( pojo, getPropertyNameFieldNames().get( clazz ) );
    }

    @Override
    public Set<?> getProperties(final T pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getProxiedSet( pojo, getPropertiesFieldNames().get( clazz ) );
    }

    @Override
    public boolean accepts(final Class<?> pojoClass) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojoClass  );
        return getPropertyNameFieldNames().containsKey( clazz );
    }
}
