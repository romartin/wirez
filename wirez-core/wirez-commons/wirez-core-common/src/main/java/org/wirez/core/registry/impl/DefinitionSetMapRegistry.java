package org.wirez.core.registry.impl;

import org.uberfire.mvp.Command;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.registry.definition.TypeDefinitionSetRegistry;

import java.util.Collection;
import java.util.HashMap;

class DefinitionSetMapRegistry<T> extends AbstractDynamicRegistryWrapper<T, MapRegistry<T>> implements TypeDefinitionSetRegistry<T> {

    private DefinitionManager definitionManager;
    private boolean initialized;
    private Command lazyInitializationCallback;

    DefinitionSetMapRegistry( final DefinitionManager definitionManager ) {
        super(
                new MapRegistry<T>(
                    item -> null != item ? definitionManager.adapters().forDefinitionSet().getId( item ) : null,
                    new HashMap<String, T>() )
            );
        this.definitionManager = definitionManager;
        this.lazyInitializationCallback = null;
        this.initialized = false;
    }

    @Override
    public void setLazyInitializationCallback( final Command callback ) {
        this.lazyInitializationCallback = callback;
    }

    @Override
    public T getDefinitionSetById( final String id ) {
        checkLazyInitialization();
        return getWrapped().getItemByKey( id );
    }

    @Override
    public T getDefinitionSetByType( final Class<T> type ) {
        final String id = BindableAdapterUtils.getDefinitionSetId( type, definitionManager );
        return getDefinitionSetById( id );
    }

    @Override
    public boolean contains( final T item ) {
        checkLazyInitialization();
        return super.contains( item );
    }

    @Override
    public Collection<T> getItems() {
        checkLazyInitialization();
        return super.getItems();
    }

    private void checkLazyInitialization() {

        if ( null != this.lazyInitializationCallback && !initialized ) {

            lazyInitializationCallback.execute();
            this.initialized = true;

        }

    }
}
