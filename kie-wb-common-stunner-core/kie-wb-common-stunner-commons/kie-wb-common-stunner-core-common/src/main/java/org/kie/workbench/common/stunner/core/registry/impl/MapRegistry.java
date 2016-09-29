package org.kie.workbench.common.stunner.core.registry.impl;

import org.kie.workbench.common.stunner.core.registry.DynamicRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

class MapRegistry<T> implements DynamicRegistry<T> {

    private final KeyProvider<T> keyProvider;
    private final java.util.Map<String, T> items;

    MapRegistry( final KeyProvider<T> keyProvider,
                        final Map<String, T> items ) {
        this.keyProvider = keyProvider;
        this.items = items;
    }

    @Override
    public void register(final T item) {
        items.put( getItemId( item ), item );
    }

    public boolean remove(final T item) {
        return null != items.remove( getItemId( item ) );
    }

    @Override
    public boolean contains(final T item) {
        return items.containsValue(item);
    }

    @Override
    public Collection<T> getItems() {
        return Collections.unmodifiableList(new ArrayList<T>(items.values()));
    }

    @Override
    public void clear() {
        items.clear();
    }

    public T getItemByKey( final String key ) {
        return items.get( key );
    }

    private String getItemId( final T item ) {
        return keyProvider.getKey( item );
    }

}
