package org.wirez.core.definition.adapter;

import org.wirez.core.rule.Rule;

import java.util.Collection;

/**
 * A Definition Set rules pojo adapter.. 
 */
public interface DefinitionSetRuleAdapter<T> extends PriorityAdapter {

    /**
     * Returns the definition set's rules for a given pojo.
     */
    Collection<Rule> getRules( T pojo );
    
}
