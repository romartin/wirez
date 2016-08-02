package org.wirez.core.registry;

public interface DynamicRegistry<T> extends Registry<T> {

    void register( T item );

    boolean remove( T item );

    void clear();

}
