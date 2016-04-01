package org.wirez.core.api.definition.adapter.binding;

import org.wirez.core.api.definition.adapter.PropertySetAdapter;

import java.util.Map;
import java.util.Set;

public abstract class BindablePropertySetAdapter<T> extends AbstractBindableAdapter<T> implements PropertySetAdapter<T> {

    protected abstract Map<Class, String> getPropertyNameFieldNames();
    protected abstract Map<Class, Set<String>> getPropertiesFieldNames();
    
    @Override
    public String getId(T pojo) {
        return getPojoId(pojo);
    }

    @Override
    public String getName(T pojo) {
        return getProxiedValue( pojo, getPropertyNameFieldNames().get(pojo.getClass()) );
    }

    @Override
    public Set<?> getProperties(T pojo) {
        return getProxiedSet( pojo, getPropertiesFieldNames().get(pojo.getClass()) );
    }

    @Override
    public boolean accepts(Class<?> pojo) {
        return getPropertyNameFieldNames().containsKey(pojo);
    }
}
