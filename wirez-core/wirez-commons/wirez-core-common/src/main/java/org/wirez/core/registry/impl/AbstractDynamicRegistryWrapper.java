package org.wirez.core.registry.impl;

import org.wirez.core.registry.DynamicRegistry;

public abstract class AbstractDynamicRegistryWrapper<T, R extends DynamicRegistry<T>>
        extends AbstractRegistryWrapper<T, R>
        implements DynamicRegistry<T> {

    protected AbstractDynamicRegistryWrapper( final R wrapped ) {
        super( wrapped );
    }

    @Override
    public void register( final T item ) {
        getWrapped().register( item );
    }

    @Override
    public boolean remove( final T item ) {
        return getWrapped().remove( item );
    }

    @Override
    public void clear() {
        getWrapped().clear();
    }

}
