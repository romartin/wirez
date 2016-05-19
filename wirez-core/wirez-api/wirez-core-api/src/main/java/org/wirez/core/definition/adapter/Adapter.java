package org.wirez.core.definition.adapter;

/**
 * Adapters provide a way to bind a given domain object of any class with the app-specific domain model .
 */
public interface Adapter<T> {

    /**
     * Check if the adapter instance supports the pojo's class.
     */
    boolean accepts ( Class<?> pojoClass );

    /**
     * As small priority value, highest priority.
     */
    int getPriority();
    
}
