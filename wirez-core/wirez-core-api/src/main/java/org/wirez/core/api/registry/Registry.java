package org.wirez.core.api.registry;

import java.util.Collection;

public interface Registry<T> {

    void add(T item);
    
    boolean contains( T item );
    
    void remove(T item);
    
    T get(String uuid);
    
    Collection<T> getItems();
    
    void clear();
    
}
