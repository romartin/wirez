package org.wirez.core.client.definition.adapter.binding;

import org.wirez.core.definition.adapter.PriorityAdapter;
import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;

import java.util.Collection;
import java.util.Set;

public abstract class AbstractClientBindableAdapter<T> implements PriorityAdapter {

    @SuppressWarnings("unchecked")
    protected <R> R getProxiedValue(final T pojo, final String fieldName) {
        return ClientBindingUtils.getProxiedValue( pojo, fieldName );
    }

    @SuppressWarnings("unchecked")
    protected <R> Set<R> getProxiedSet(final T pojo, final Collection<String> fieldNames) {
        return ClientBindingUtils.getProxiedSet( pojo, fieldNames );
    }

    @SuppressWarnings("unchecked")
    protected <V> void setProxiedValue(final T pojo, final String fieldName, final V value) {
        ClientBindingUtils.setProxiedValue( pojo, fieldName, value );
    }

    protected String getDefinitionId( Class<?> type ) {
        return BindableAdapterUtils.getDefinitionId( type );
    }

    @Override
    public boolean isPojoModel() {
        return true;
    }

    @Override
    public int getPriority() {
        return 0;
    }
    
}
