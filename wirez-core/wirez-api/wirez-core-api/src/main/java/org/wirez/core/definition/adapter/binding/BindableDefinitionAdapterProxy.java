package org.wirez.core.definition.adapter.binding;

import org.wirez.core.definition.adapter.DefinitionAdapterWrapper;

public abstract class BindableDefinitionAdapterProxy<T> extends DefinitionAdapterWrapper<T, BindableDefinitionAdapter<T>>
    implements HasInheritance {

    protected abstract void setBindings( BindableDefinitionAdapter<T> adapter );

    protected BindableDefinitionAdapterProxy() {

    }
    @SuppressWarnings("unchecked")
    public BindableDefinitionAdapterProxy( final BindableAdapterFactory adapterFactory ) {
        super( adapterFactory.newBindableDefinitionAdapter() );
        setBindings( adapter );
    }

    @Override
    public String getBaseType( final Class<?> type ) {
        return adapter.getBaseType( type );
    }

    @Override
    public String[] getTypes( final String baseType ) {
        return adapter.getTypes( baseType );
    }

    @Override
    public boolean isPojoModel() {
        return true;
    }

}
