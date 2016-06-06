package org.wirez.core.definition.adapter.binding;

import org.wirez.core.definition.adapter.PropertySetAdapterWrapper;

public abstract class BindablePropertySetAdapterProxy<T> extends PropertySetAdapterWrapper<T, BindablePropertySetAdapter<T>> {
    
    protected abstract void setBindings( BindablePropertySetAdapter<T> adapter );

    protected BindablePropertySetAdapterProxy() {

    }

    @SuppressWarnings("unchecked")
    public BindablePropertySetAdapterProxy( final BindableAdapterFactory adapterFactory ) {
        super( adapterFactory.newBindablePropertySetAdapter() );
        setBindings( adapter );
    }
    
}
