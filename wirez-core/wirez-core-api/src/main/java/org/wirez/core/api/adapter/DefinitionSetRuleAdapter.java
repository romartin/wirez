package org.wirez.core.api.adapter;

import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.PropertySet;
import org.wirez.core.api.rule.Rule;

import java.util.Collection;
import java.util.Set;

public interface DefinitionSetRuleAdapter<T extends DefinitionSet> extends Adapter<T> {

    Collection<Rule> getRules(T pojo);
    
}
