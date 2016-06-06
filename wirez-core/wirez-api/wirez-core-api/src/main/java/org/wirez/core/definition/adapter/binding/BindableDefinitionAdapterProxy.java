package org.wirez.core.definition.adapter.binding;

import org.wirez.core.definition.adapter.DefinitionAdapterWrapper;

public abstract class BindableDefinitionAdapterProxy<T> extends DefinitionAdapterWrapper<T, BindableDefinitionAdapter<T>> {

    protected abstract void setBindings( BindableDefinitionAdapter<T> adapter );

    protected BindableDefinitionAdapterProxy() {

    }
    @SuppressWarnings("unchecked")
    public BindableDefinitionAdapterProxy( final BindableAdapterFactory adapterFactory ) {
        super( adapterFactory.newBindableDefinitionAdapter() );
        setBindings( adapter );
    }
    
}
