package org.wirez.core.api.adapter;

public interface Adapter<T> {
    
    boolean accepts ( Class pojoClass );

    /**
     * As small priority value, highest priority.
     */
    int getPriority();
    
}
