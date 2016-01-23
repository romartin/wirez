package org.wirez.core.api.adapter;

public interface Adapter<T> {
    
    boolean accepts ( Object pojo );

    /**
     * As small priority value, highest priority.
     */
    int getPriority();
    
}
