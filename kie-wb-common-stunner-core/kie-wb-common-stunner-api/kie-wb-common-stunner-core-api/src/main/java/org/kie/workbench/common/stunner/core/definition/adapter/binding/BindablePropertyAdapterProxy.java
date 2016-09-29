package org.kie.workbench.common.stunner.core.definition.adapter.binding;

import org.kie.workbench.common.stunner.core.definition.adapter.PropertyAdapterWrapper;

public abstract class BindablePropertyAdapterProxy<T, V> extends PropertyAdapterWrapper<T, V, BindablePropertyAdapter<T, V>> {
    
    protected abstract void setBindings( BindablePropertyAdapter<T, V> adapter );

    protected BindablePropertyAdapterProxy() {

    }

    @SuppressWarnings("unchecked")
    public BindablePropertyAdapterProxy( final BindableAdapterFactory adapterFactory ) {
        super( adapterFactory.newBindablePropertyAdapter() );
        setBindings( adapter );
    }

    @Override
    public boolean isPojoModel() {
        return true;
    }

}
