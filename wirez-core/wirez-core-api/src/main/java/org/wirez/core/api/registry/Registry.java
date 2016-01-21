package org.wirez.core.api.registry;

import java.util.Collection;

public interface Registry<T> {
    
    Collection<T> getItems();
    
    void clear();
    
}
