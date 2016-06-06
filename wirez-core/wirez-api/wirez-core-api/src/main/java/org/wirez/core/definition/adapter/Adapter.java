package org.wirez.core.definition.adapter;

/**
 * Adapters provide a way to bind a given domain object of any class with the app-specific domain model .
 */
public interface Adapter {

    /**
     * Check if the adapter instance supports the pojo's type.
     */
    boolean accepts ( Class<?> type );

}
