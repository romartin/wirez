package org.kie.workbench.common.stunner.core.registry.impl;

import org.kie.workbench.common.stunner.core.definition.adapter.AdapterManager;
import org.kie.workbench.common.stunner.core.definition.adapter.binding.BindableAdapterUtils;
import org.kie.workbench.common.stunner.core.registry.definition.TypeDefinitionSetRegistry;

import java.util.Collection;
import java.util.HashMap;

class DefinitionSetMapRegistry<T> extends AbstractDynamicRegistryWrapper<T, MapRegistry<T>> implements TypeDefinitionSetRegistry<T> {

    private AdapterManager adapterManager;

    DefinitionSetMapRegistry( final AdapterManager adapterManager ) {
        super(
                new MapRegistry<T>(
                    item -> null != item ? adapterManager.forDefinitionSet().getId( item ) : null,
                    new HashMap<String, T>() )
            );
        this.adapterManager = adapterManager;
    }

    @Override
    public T getDefinitionSetById( final String id ) {
        return getWrapped().getItemByKey( id );
    }

    @Override
    public T getDefinitionSetByType( final Class<T> type ) {
        final String id = BindableAdapterUtils.getDefinitionSetId( type, adapterManager.registry() );
        return getDefinitionSetById( id );
    }

    @Override
    public boolean contains( final T item ) {
        return super.contains( item );
    }

    @Override
    public Collection<T> getItems() {
        return super.getItems();
    }

}
