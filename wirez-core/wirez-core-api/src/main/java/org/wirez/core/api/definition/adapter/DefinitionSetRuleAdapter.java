package org.wirez.core.api.definition.adapter;

import org.wirez.core.api.rule.Rule;

import java.util.Collection;

public interface DefinitionSetRuleAdapter<T> extends Adapter<T> {

    Collection<Rule> getRules(T pojo);
    
}
