package org.wirez.core.api.adapter.shared;

import org.wirez.core.api.adapter.DefinitionSetAdapter;
import org.wirez.core.api.adapter.DefinitionSetRuleAdapter;
import org.wirez.core.api.definition.DefaultDefinitionSet;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.PropertySet;
import org.wirez.core.api.rule.Rule;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collection;
import java.util.Set;

@ApplicationScoped
public class DefaultDefinitionSetRuleAdapter implements DefinitionSetRuleAdapter<DefaultDefinitionSet> {

    @Override
    public boolean accepts(final Object pojo) {
        return pojo instanceof DefaultDefinitionSet;
    }
    
    @Override
    public Collection<Rule> getRules(final DefaultDefinitionSet pojo) {
        Collection<Rule> result = null;
        if ( null != pojo ) {
            result = pojo.getRules();
        }

        return result;
    }

    @Override
    public int getPriority() {
        return 1;
    }
    
}
