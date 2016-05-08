package org.wirez.core.api.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public abstract class BaseMapRegistry<T> implements Registry<T> {

    protected final java.util.Map<String, T> items;

    public BaseMapRegistry(final java.util.Map<String, T> items) {
        this.items = items;
    }

    protected abstract String getItemId(final T item);
    
    public void add(final T item) {
        items.put( getItemId( item ), item );
    }

    public void remove(final T item) {
        items.remove( getItemId( item ) );
    }

    @Override
    public T get( final String id ) {
        return items.get( id );
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
}
