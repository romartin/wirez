package org.wirez.core.api.definition;

/**
 * Definition Set marker interface for further CDI look-ups.
 * It proxies the definition sets on annotated models.
 */
public interface DefinitionSetProxy<T> {
    
    T getDefinitionSet();
    
}
