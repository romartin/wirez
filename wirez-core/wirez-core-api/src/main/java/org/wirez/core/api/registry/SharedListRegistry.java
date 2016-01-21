package org.wirez.core.api.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class SharedListRegistry<T> implements Registry<T> {

    protected final List<T> items = new ArrayList<T>();

    protected abstract String getItemId(T item);
    
    protected void add(final T item) {
        items.add(item);
    }

    protected void remove(final T item) {
        items.remove(item);
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
