package org.wirez.core.registry.impl;

import org.wirez.core.registry.DynamicRegistry;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

class ListRegistry<T> implements DynamicRegistry<T> {

    private final KeyProvider<T> keyProvider;
    private final List<T> items;

    public ListRegistry( final KeyProvider<T> keyProvider, final List<T> items ) {
        this.keyProvider = keyProvider;
        this.items = items;
    }

    public void add( final int pos, final T item ) {
        items.add( pos, item );
    }

    @Override
    public void register( final T item ) {
        items.add( item );
    }

    public boolean remove( final T item ) {
        return items.remove( item );
    }

    @Override
    public boolean contains( final T item ) {
        return items.contains( item );
    }

    @Override
    public Collection<T> getItems() {
        return Collections.unmodifiableList( items );
    }

    public T getItemByKey( final String id ) {
        if ( null != id ) {
            for ( final T item : items ) {
                final String itemId = getItemKey( item );
                if ( id.equals( itemId ) ) {
                    return item;
                }
            }
        }
        return null;
    }

    @Override
    public void clear() {
        items.clear();
    }

    public int indexOf( final T item ) {
        return items.indexOf( item );
    }

    private String getItemKey( final T item ) {
        return keyProvider.getKey( item );
    }

}
