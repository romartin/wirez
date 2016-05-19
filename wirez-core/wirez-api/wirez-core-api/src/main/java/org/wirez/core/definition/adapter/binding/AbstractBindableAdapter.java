package org.wirez.core.definition.adapter.binding;

import org.jboss.errai.databinding.client.HasProperties;
import org.jboss.errai.databinding.client.api.DataBinder;
import org.wirez.core.definition.adapter.Adapter;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class AbstractBindableAdapter<T> implements Adapter<T> {

    protected <R> R getProxiedValue(final T pojo, final String fieldName) {
        R result = null;
        if ( null != pojo ) {
            HasProperties hasProperties = (HasProperties) DataBinder.forModel(pojo).getModel();
            result = (R) hasProperties.get(fieldName);
        }
        return result;
    }

    protected <R> Set<R> getProxiedSet(final T pojo, final Collection<String> fieldNames) {
        Set<R> result = null;
        if ( null != pojo ) {
            result = new LinkedHashSet<>();
            for ( String fieldName : fieldNames ) {
                HasProperties hasProperties = (HasProperties) DataBinder.forModel(pojo).getModel();
                result.add( (R) hasProperties.get(fieldName) );
            }
        }
        return result;
    }
    
    protected <V> void setProxiedValue(final T pojo, final String fieldName, final V value) {
        if ( null != pojo ) {
            HasProperties hasProperties = (HasProperties) DataBinder.forModel(pojo).getModel();
            hasProperties.set(fieldName, value);
        }
    }

    @Override
    public int getPriority() {
        return 0;
    }
    
}
