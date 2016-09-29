package org.kie.workbench.common.stunner.core.definition.adapter.binding;

import org.kie.workbench.common.stunner.core.definition.adapter.PropertySetAdapterWrapper;

public abstract class BindablePropertySetAdapterProxy<T> extends PropertySetAdapterWrapper<T, BindablePropertySetAdapter<T>> {
    
    protected abstract void setBindings( BindablePropertySetAdapter<T> adapter );

    protected BindablePropertySetAdapterProxy() {

    }

    @SuppressWarnings("unchecked")
    public BindablePropertySetAdapterProxy( final BindableAdapterFactory adapterFactory ) {
        super( adapterFactory.newBindablePropertySetAdapter() );
        setBindings( adapter );
    }

    @Override
    public boolean isPojoModel() {
        return true;
    }

}
