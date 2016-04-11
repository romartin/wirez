package org.wirez.core.api.definition.adapter;

import org.wirez.core.api.rule.Rule;

import java.util.Collection;

/**
 * A Definition Set rules pojo adapter.. 
 */
public interface DefinitionSetRuleAdapter<T> extends Adapter<T> {

    /**
     * Returns the definition set's rules for a given pojo.
     */
    Collection<Rule> getRules(T pojo);
    
}
