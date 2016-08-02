package org.wirez.core.registry.impl;

import org.wirez.core.registry.Registry;

import java.util.Collection;

public abstract class AbstractRegistryWrapper<T, R extends Registry<T>> implements Registry<T> {

    private final R wrapped;

    protected AbstractRegistryWrapper( final R wrapped ) {
        this.wrapped = wrapped;
    }

    @Override
    public boolean contains( final T item ) {
        return wrapped.contains( item );
    }

    @Override
    public Collection<T> getItems() {
        return wrapped.getItems();
    }

    protected R getWrapped() {
        return wrapped;
    }

}
