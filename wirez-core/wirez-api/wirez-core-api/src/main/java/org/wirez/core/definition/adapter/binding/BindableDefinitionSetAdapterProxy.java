package org.wirez.core.definition.adapter.binding;

import org.wirez.core.definition.adapter.DefinitionSetAdapterWrapper;

public abstract class BindableDefinitionSetAdapterProxy<T> extends DefinitionSetAdapterWrapper<T, BindableDefinitionSetAdapter<T>> {

    protected abstract void setBindings( BindableDefinitionSetAdapter<T> adapter );

    protected BindableDefinitionSetAdapterProxy() {

    }

    @SuppressWarnings("unchecked")
    public BindableDefinitionSetAdapterProxy( final BindableAdapterFactory adapterFactory ) {
        super( adapterFactory.newBindableDefinitionSetAdapter() );
        setBindings( adapter );
    }
    
}
