package org.wirez.core.api.definition.adapter;

public interface Adapter<T> {
    
    boolean accepts ( Class<?> pojo );

    /**
     * As small priority value, highest priority.
     */
    int getPriority();
    
}
