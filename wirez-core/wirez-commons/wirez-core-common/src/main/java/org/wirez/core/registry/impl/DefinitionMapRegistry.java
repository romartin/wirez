package org.wirez.core.registry.impl;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.registry.definition.DefinitionRegistry;
import org.wirez.core.registry.definition.TypeDefinitionRegistry;

import java.util.HashMap;

class DefinitionMapRegistry<T> extends AbstractDynamicRegistryWrapper<T, MapRegistry<T>> implements TypeDefinitionRegistry<T> {

    private DefinitionManager definitionManager;

    DefinitionMapRegistry( final DefinitionManager definitionManager ) {
        super(
                new MapRegistry<T>(
                    item -> null != item ? definitionManager.adapters().forDefinition().getId( item ) : null,
                    new HashMap<String, T>() )
            );
        this.definitionManager = definitionManager;
    }

    @Override
    public T getDefinitionById( final String id ) {
        return getWrapped().getItemByKey( id );
    }

    @Override
    public T getDefinitionByType( final Class<T> type ) {

        final String id = BindableAdapterUtils.getDefinitionId( type, definitionManager );

        return getDefinitionById( id );

    }


}
