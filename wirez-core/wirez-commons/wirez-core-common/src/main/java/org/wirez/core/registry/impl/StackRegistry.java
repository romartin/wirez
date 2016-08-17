package org.wirez.core.registry.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Stack;

import org.wirez.core.registry.DynamicRegistry;

class StackRegistry<T> implements DynamicRegistry<T> {

    private final KeyProvider<T> keyProvider;
    private final Stack<T> items;

    public StackRegistry(final KeyProvider<T> keyProvider, final Stack<T> items) {
        this.keyProvider = keyProvider;
        this.items = items;
    }

    public T peek() {
        return items.peek();
    }

    public T pop() {
        return items.pop();
    }

    @Override
    public void register(final T item) {
        items.add(item);
    }

    public boolean remove(final T item) {
        return items.remove(item);
    }

    @Override
    public boolean contains(final T item) {
        return items.contains(item);
    }

    @Override
    public Collection<T> getItems() {
        return Collections.unmodifiableList(items);
    }

    public T getItemByKey(final String id) {
        if (null != id) {
            for (final T item : items) {
                final String itemId = getItemKey(item);
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

    public int indexOf(final T item) {
        return items.indexOf(item);
    }

    private String getItemKey(final T item) {
        return keyProvider.getKey(item);
    }

    Stack<T> getStack() {
        return items;
    }

}
