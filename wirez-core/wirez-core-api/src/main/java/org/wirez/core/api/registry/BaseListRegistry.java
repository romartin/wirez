package org.wirez.core.api.registry;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class BaseListRegistry<T> implements Registry<T> {

    protected final List<T> items;

    public BaseListRegistry(final List<T> items) {
        this.items = items;
    }

    protected abstract String getItemId(final T item);
    
    public void add(final T item) {
        items.add(item);
    }

    public void remove(final T item) {
        items.remove(item);
    }

    @Override
    public T get(String uuid) {
        if ( null != uuid ) {
            for (T item : items) {
                final String id = getItemId(item);
                if ( id.equals(uuid) ) {
                    return item;
                }
            }
        }
        return null;
    }

    @Override
    public boolean contains(final T item) {
        return items.contains(item);
    }

    @Override
    public Collection<T> getItems() {
        return Collections.unmodifiableList(items);
    }

    protected T getItem(String id) {
        if ( null != id ) {
            for (final T item : items) {
                final String itemId = getItemId(item);
                if (id.equals(itemId)) {
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
}
